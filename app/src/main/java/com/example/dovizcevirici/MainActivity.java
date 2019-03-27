package com.example.dovizcevirici;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tryt,cadt,usdt,jpyt,chft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        tryt = (TextView)findViewById(R.id.try_t);
        cadt = (TextView)findViewById(R.id.cad_t);
        usdt = (TextView)findViewById(R.id.usd_t);
        jpyt = (TextView)findViewById(R.id.jpj_t);
        chft = (TextView)findViewById(R.id.chf_t);


    }

    public  void getRates(View view){

        DownloadData downloadData = new DownloadData();

        try {
            String url = "http://data.fixer.io/api/latest?access_key=ffc17e06399635f0a2d801715dcdfb30&format=1";

            downloadData.execute(url);
        }catch (Exception e){

        }
    }
    //ilk i string url,proges bar istemioz void,ben sana cevap ne vereyeim dio oda strin
    private class DownloadData extends AsyncTask<String,Void,String>{

        //neden senkronize olmayan bi işlem yapıoz çünkü api ye girdimizde aynı anda thread leri olması api kitleyebili.
        //api kitlememek için arka planda api indircez
        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                //baglantı acma
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                //gelen baglantıdan veri okuma
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);

                int data = inputStream.read();

                while (data >0 ){
                    char character = (char) data;
                    result += character;
                    //bir sonraki karakteri oku
                    data = inputStream.read();
                }

                return result;

            }catch (Exception e){
                return null;
            }

        }
        //bu islem bitince ne olsun
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

           // System.out.println("alınan data :"+s);
//eger json "[]" bu sekilde baslar array dir. eger "{}" baslarsa objedir
            try {

                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                //System.out.println("base:" + base);

                String rates = jsonObject.getString("rates");

                //iç içe objelerde

                JSONObject jsonObject1 = new JSONObject(rates);
                String turkishlira = jsonObject1.getString("TRY");
                tryt.setText("TRY: " + turkishlira);

                String usd = jsonObject1.getString("USD");
                usdt.setText("USD: " + usd);

                String cad = jsonObject1.getString("CAD");
                cadt.setText("CAD: " + cad);

                String chf = jsonObject1.getString("CHF");
                chft.setText("CHF: " + chf);

                String jpy = jsonObject1.getString("JPY");
                jpyt.setText("JPY: " + jpy);

            } catch (Exception e) {

            }


        }
    }



}
