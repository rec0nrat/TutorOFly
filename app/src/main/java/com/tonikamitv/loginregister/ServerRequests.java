package com.tonikamitv.loginregister;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://52.34.0.76/";
    //public static final String SERVER_ADDRESS = "http://www.cbokit.com/";


    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }

    //User Login/Register methods
    public void storeUserDataInBackground(User user, GetUserCallback userCallBack) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallBack).execute();
    }

    public void fetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, userCallBack).execute();
    }

    //Message Post/Sync methods
    public void storeUserMessageInBackground(User user, Message message, GetMessageCallback userCallBack){
        progressDialog.show();
        new StoreUserMessageAsyncTask(user, message, userCallBack).execute();
    }

    public void fetchMessagesAsyncTask(GetMessageCallback userMessageCallback) {
        progressDialog.show();
        new FetchMessagesInBackground(userMessageCallback).execute();
    }

    /**tweissMess
     * parameter sent to task upon execution progress published during
     * background computation result of the background computation
     */

    public class StoreUserMessageAsyncTask extends AsyncTask<Void, Void, Void>{
        User user;
        GetMessageCallback userCallBack;
        Message message;

        public StoreUserMessageAsyncTask(User user, Message message, GetMessageCallback userCallBack){
            this.user = user;
            this.userCallBack = userCallBack;
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();

            dataToSend.add(new BasicNameValuePair("init_text", message.getInit_msg_txt()));
            dataToSend.add(new BasicNameValuePair("user_name", message.getUsername()));
            dataToSend.add(new BasicNameValuePair("time_stamp", message.getInit_time_stamp()));
            dataToSend.add(new BasicNameValuePair("last_update", message.getLast_update()));
            dataToSend.add(new BasicNameValuePair("post_id", message.getPost_id() + ""));
            dataToSend.add(new BasicNameValuePair("user_id", user.id + ""));
            dataToSend.add(new BasicNameValuePair("tag_id", message.getTag_id() + ""));
            dataToSend.add((new BasicNameValuePair("title", message.getTitle())));
            dataToSend.add(new BasicNameValuePair("anonymous", (message.is_anon())?1+"":0+""));
            dataToSend.add(new BasicNameValuePair("help", 1+""));
            dataToSend.add(new BasicNameValuePair("solved", 0+""));
            dataToSend.add(new BasicNameValuePair("comments", message.getComment_cnt()+""));
            dataToSend.add(new BasicNameValuePair("numLikes", message.getLikes_cnt()+""));



            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

          //  Log.i("Username: ", user.username);
          //  Log.i("Message: ", message);
           // Log.i("StudentID: ", user.age +"");

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "post_message_android.php");

            Log.i("Message", "Data: " + dataToSend.toString());

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            userCallBack.done(null);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("fname", user.name_first));
            dataToSend.add(new BasicNameValuePair("lname", user.name_last));
            dataToSend.add(new BasicNameValuePair("id", user.id + ""));
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            dataToSend.add(new BasicNameValuePair("email", user.email));
            dataToSend.add(new BasicNameValuePair("user_type", user.user_type.toString()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            //HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "register_android.php");

            //User returnedUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                //client.execute(post);


                HttpResponse httpResponse = client.execute(post);
/*
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() != 0){

                    String fname = jObject.getString("fname"),
                            lname = jObject.getString("lname"),
                            username = jObject.getString("username"),
                            password = jObject.getString("password"),
                            email = jObject.getString("email");
                            //user_type_str = jObject.getString("user_type");
                    int id = jObject.getInt("id");
                    User.user_types user_type = User.user_types.student;


                   // if(user_type_str.equals("student"))  user_type = User.user_types.student;
                   // else user_type = User.user_types.student;


                    returnedUser = new User( fname, lname, id, username, password, email, user_type);
                    */

            } catch (Exception e) {
                e.printStackTrace();
            }
//            Log.i("The New User: ", returnedUser.username);

            return null;
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            userCallBack.done(null);
        }

    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallBack;

        public fetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            Log.i("TYLERS INFO: " , user.username + user.password);


            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);


            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "login_android.php");

            User returnedUser = new User(user.username, user.password);

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                Log.i("Login", "Returned Login Results: " + entity);

                String result = EntityUtils.toString(entity);
                Log.i("Login", "Returned Login Results: " + result);
                //JSONArray jarray = new JSONArray(result);
                JSONObject jObject = new JSONObject(result);


                if (jObject.length() != 0){
                    String  fname = jObject.getString("first_name"),
                            lname = jObject.getString("last_name"),
                            username = jObject.getString("user_name"),
                            password = user.password,
                            email = jObject.getString("email");
                           // user_type_str = jObject.getString("user_type");
                    int id = jObject.getInt("user_id");
                    User.user_types user_type = User.user_types.student;


                   // if(user_type_str.equals("student"))  user_type = User.user_types.student;
                   // else user_type = User.user_types.student;


                    returnedUser = new User( fname, lname, id, username, password, email, user_type);
                    Log.i("Login", "returnedUSer: " + returnedUser.id);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }
        //**********************************************

        @Override
        protected void onPostExecute(User returnedUser) {
            super.onPostExecute(returnedUser);
            progressDialog.dismiss();
            userCallBack.done(returnedUser);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }



    //**********************************************
    public class FetchMessagesInBackground extends AsyncTask<Void, Void, ArrayList<Message>> {
        //User user;
        GetMessageCallback userMessageCallBack;

        public FetchMessagesInBackground( GetMessageCallback userMessageCallBack) {
            //this.user = user;
            this.userMessageCallBack = userMessageCallBack;
        }

        @Override
        protected ArrayList<Message> doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            //dataToSend.add(new BasicNameValuePair("username", user.username));
           // dataToSend.add(new BasicNameValuePair("password", user.password));
            //Log.i("TYLERS INFO: ", user.username + user.password);

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);


            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "get_messages_help.php");

            ArrayList<Message> returnedMessages = new ArrayList<Message>();

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);

                Log.i("JSON TEST", " " + result);
                //JSONArray jarray = new JSONArray(result);
                JSONObject jObject = new JSONObject(result);
                Iterator<String> keys = jObject.keys();
                //JSONArray jarray = in.;


                if (jObject.length() != 0) {

                    while (keys.hasNext()) {

                        JSONObject message = jObject.getJSONObject(keys.next());

                        String title = message.getString("title"),
                                init_text = message.getString("init_text"),
                                username = message.getString("user_name"),
                                time_stamp = message.getString("time_stamp"),
                                last_update = message.getString("last_update");
                        int     post_id = message.getInt("post_id"),
                                user_id = message.getInt("user_id"),
                                tag_id = message.getInt("tag_id"),
                                help_cnt = message.getInt("help"),
                                comment_cnt = message.getInt("comments"),
                                solved_cnt = message.getInt("solved"),
                                likes_cnt = message.getInt("numLikes");

                        boolean anonymous = (message.getInt("anonymous") == 0) ? false : true;




                        returnedMessages.add(0,new Message(init_text,username,time_stamp,last_update,post_id,user_id,tag_id,help_cnt,comment_cnt,solved_cnt,likes_cnt,anonymous));

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedMessages;
        }


        //**********************************************

        @Override
        protected void onPostExecute(ArrayList<Message> returnedMessages) {
            //Void returnedMessages;
            super.onPostExecute(returnedMessages);
            progressDialog.dismiss();
            userMessageCallBack.done(returnedMessages);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }
}
