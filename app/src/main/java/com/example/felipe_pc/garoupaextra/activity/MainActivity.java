package com.example.felipe_pc.garoupaextra.activity;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.felipe_pc.garoupaextra.R;
import com.example.felipe_pc.garoupaextra.helper.Permissao;
import com.example.felipe_pc.garoupaextra.helper.RegEx;
import com.example.felipe_pc.garoupaextra.helper.SmsBroadcastReceiver;
import com.example.felipe_pc.garoupaextra.model.Gasto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 0;
    private static final String BRADESCO_CARTAO = "(\\d{4})";
    private static final String BRADESCO_DATA = "(\\d{2}/\\d{2}/\\d{4})";
    private static final String BRADESCO_HORA = "(\\d{2}:\\d{2})";
    private static final String BRADESCO_VALOR = "R\\$\\s*(\\d+,\\d+)";
    private static final String BRADESCO_LOCAL = "R\\$\\s*\\d*,\\d*\\s*(.*).";
    String[] from = {"listview_image", "listview_title", "listview_discription"};
    int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};
    ListView androidListView;
    private SmsBroadcastReceiver smsBroadcastReceiver;
    private String[] permissoesNecessarias = new String[]{
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.RECEIVE_SMS
    };

    // Firebase
    private DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference gastoReferencia = firebaseReferencia.child("gasto");

    // Array of strings for ListView Title
    String[] listviewTitle = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
    };


    int[] listviewImage = new int[]{
            R.drawable.cat_icon_outros, R.drawable.cat_icon_outros, R.drawable.cat_icon_outros, R.drawable.cat_icon_outros,
            R.drawable.cat_icon_outros, R.drawable.cat_icon_outros, R.drawable.cat_icon_outros, R.drawable.cat_icon_outros,
    };

    String[] listviewShortDescription = new String[]{
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //O primeiro parâmetro é o request code, apenas para controle interno, para saber de onde veio o pedido.
        Permissao.validaPermissoes(1, MainActivity.this,permissoesNecessarias);

        smsBroadcastReceiver = new SmsBroadcastReceiver();
        registerReceiver(smsBroadcastReceiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));

        smsBroadcastReceiver.setListener(new SmsBroadcastReceiver.Listener() {
            @Override
            public void onTextReceived(String r, String m) {
                Toast.makeText(MainActivity.this, "A salvar", Toast.LENGTH_SHORT).show();
                if(/*r.equals("27888") &&*/ m.contains("COMPRA APROVADA")){
                    RegEx regEx = new RegEx(m);

                    String final_cartao = regEx.buscarRegex(BRADESCO_CARTAO);
                    String data = regEx.buscarRegex(BRADESCO_DATA);
                    String hora = regEx.buscarRegex(BRADESCO_HORA);
                    String valor = regEx.buscarRegex(BRADESCO_VALOR);
                    String local = regEx.buscarRegex(BRADESCO_LOCAL);
                    double valorDouble = Double.parseDouble(valor.replace(",","."));

                    Gasto gasto = new Gasto();
                    gasto.setValor(valorDouble);
                    gasto.setData(data);
                    gasto.setLocal(local);
                    gasto.setBanco_det(final_cartao);
                    gasto.setHora(hora);

                    String gastoId =  gastoReferencia.push().getKey();
                    gastoReferencia.child(gastoId).setValue(gasto);
                    Log.i("SMS_RECEBIDO", final_cartao + " - " + data + " - " + hora + " - " + valor + " - " + local);

                }
            }
        });

        androidListView = (ListView) findViewById(R.id.list_view);

        atualizarVisaoBanco();

        //recarregarTela();
        /*List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_activity, from, to);
        androidListView.setAdapter(simpleAdapter);*/
    }
/*
    private void recarregarTela() {
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_activity, from, to);
        androidListView.setAdapter(simpleAdapter);
    }
*/
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int resultado : grantResults){

            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }

        }
    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para continuar utilizando este App, é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void atualizarVisaoBanco(){
        gastoReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<HashMap<String, String>> listaViewBanco = new ArrayList<HashMap<String, String>>();
                List<Gasto> gastosRecuperados = new ArrayList<Gasto>();

                for (DataSnapshot gastoSnapshot: dataSnapshot.getChildren()) {
                    Gasto g = gastoSnapshot.getValue(Gasto.class);
                    gastosRecuperados.add(g);

                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("listview_title", g.getLocal());
                    hm.put("listview_discription", g.getData());
                    hm.put("listview_image", Integer.toString(g.getCategoria().getImg_cat()));
                    listaViewBanco.add(hm);

                    Log.d("BANCO", "Adicionado à lista - Gasto vaor: " + g.getValor() + ", local " + g.getLocal());
                }

                SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), listaViewBanco, R.layout.listview_activity, from, to);
                androidListView.setAdapter(simpleAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("BANCO", "Failed to read value.", error.toException());
            }
        });
    }
}
