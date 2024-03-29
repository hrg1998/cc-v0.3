package com.crossclassify.trackersdk.service.repository


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.codelab.android.datastore.ConfigPreferences
import com.crossclassify.trackersdk.model.FormMetaData
import com.crossclassify.trackersdk.service.config.Api
import com.crossclassify.trackersdk.utils.objects.ConfigPreferencesSerializer
import com.crossclassify.trackersdk.utils.objects.DATA_STORE_FILE_NAME
import com.crossclassify.trackersdk.utils.objects.UrlHandler
import com.crossclassify.trackersdk.utils.objects.Values
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FormsRepository(private val context: Context) {
    companion object{
        private val Context.config: DataStore<ConfigPreferences> by dataStore(
            fileName = DATA_STORE_FILE_NAME,
            serializer = ConfigPreferencesSerializer
        )
    }
    private lateinit var forms: List<String>
    private var siteId: Int = -1
    init {
        checkDataStore()
    }

    fun sendData(
        formMetaData: FormMetaData, formName: String
    ): Any {

        return try {
            if (formName !in forms) {
                var newFormPath = UrlHandler.generateNewFormUrl(formMetaData, formName)
                var baseUrl =""
                when(Values.CC_API){
                    0 ->{
                        baseUrl = "https://9a2n6dh7ae.execute-api.ap-southeast-2.amazonaws.com/"
                    }
                    1->{
                        baseUrl ="https://7afy3zglhe.execute-api.ap-southeast-2.amazonaws.com/"
                    }
                    2->{
                        baseUrl ="https://i1uaiuond3.execute-api.ap-southeast-2.amazonaws.com/"
                    }
                }
                newFormPath = baseUrl + newFormPath
                val newFormCall = Api.client(context).sendData(newFormPath)
                Timber.tag("crossClassifyGenerateNewForm: ").i(newFormPath)
                newFormCall.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Timber.tag("crossClassify: ").i("success")
                            addFormToDataStore(formName)

                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Timber.tag("crossClassify: ").i(t.message.toString())
                    }
                })
            }

            if(formMetaData.fieldsMetaData.isNotEmpty()) {
                /** Generate Url **/
                var metaDataPath = UrlHandler.generateMetaDataUrl(formMetaData)
                var baseUrl=""
                when(Values.CC_API){
                    0 ->{
                        baseUrl = "https://9a2n6dh7ae.execute-api.ap-southeast-2.amazonaws.com/"
                    }
                    1->{
                        baseUrl ="https://7afy3zglhe.execute-api.ap-southeast-2.amazonaws.com/"
                    }
                    2->{
                        baseUrl ="https://i1uaiuond3.execute-api.ap-southeast-2.amazonaws.com/"
                    }
                }
                metaDataPath = baseUrl+metaDataPath
                Timber.tag("crossClassifySentMetaData: ").i(metaDataPath)
                /** Send API Request **/
                val call = Api.client(context).sendData(metaDataPath)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful)
                            Timber.tag("crossClassify: ").i("success")
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Timber.tag("crossClassify: ").i(t.message.toString())
                    }

                })
            }else{

            }

        } catch (e: Exception) {
            Timber.i(e.message.toString())

        }
    }

    private fun checkDataStore() {
        CoroutineScope(Dispatchers.IO).launch {
            context.config.data.collect {
                siteId = it.siteId
                forms = it.formsList
            }
        }
    }

    private fun setSiteIdDataStore(siteId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            context.config.updateData { preferences ->
                preferences.toBuilder().setSiteId(siteId).build()
            }
        }
    }

    private fun addFormToDataStore(form: String) {
        CoroutineScope(Dispatchers.IO).launch {
            context.config.updateData { preferences ->
                preferences.toBuilder().addForms(form).build()
            }
        }
    }
}

