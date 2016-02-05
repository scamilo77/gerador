package com.scamilo.apps.gerador;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Index extends AppCompatActivity {

    private SlidingPaneLayout mSlidingLayout;

    private ListView mList;

    private ListView lst;

    private TicketSet ticketSet;

    private double valorAposta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ticketSet = new TicketSet();
        mSlidingLayout = (SlidingPaneLayout)
                findViewById(R.id.sliding_pane_layout);
        mSlidingLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Toast.makeText(panel.getContext(), "Teste", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });

        String[] opcoes = new String[] {
                "Valor da Aposta", "Filtrar Dezenas", "Bolão" };

        mList = (ListView) findViewById(R.id.left_pane);
        mList.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                opcoes));
       mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, final View v, final int position, long id) {
               //
               //Toast.makeText(parent.getContext(), "Teste " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

               String source = String.valueOf(((TextView) v).getText());

               if(source.contains("Valor da Aposta"))
               {
                   final AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                   builder1.setMessage("Valor da Aposta");
                   builder1.setTitle("");
                   builder1.setCancelable(true);

                   final EditText inputText = new EditText(v.getContext());
                   inputText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                   inputText.addTextChangedListener(new MoneyTextWatcher(inputText));
                   //
                   builder1.setView(inputText);

                   if(valorAposta != 0){
                       inputText.setText(String.valueOf(valorAposta));
                   }

                   builder1.setPositiveButton(
                           "OK",
                           new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   String value = String.valueOf(inputText.getText()).replace("R", "").replace("$", "");
                                   valorAposta = Double.parseDouble(value);
                                   ((TextView) v).setText("Valor da Aposta: R$ " + NumberFormat.getCurrencyInstance().format(Double.parseDouble(value)).replace("R", "").replace("$", ""));
                                   InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                   imm.toggleSoftInput(InputMethodManager.RESULT_SHOWN, 0);
                               }
                           });

                   builder1.setNegativeButton(
                           "Cancelar",
                           new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   dialog.cancel();
                               }
                           });

                   AlertDialog alert11 = builder1.create();
                   alert11.show();
               }
           }
       });
        mList.setClickable(true);

        NumberPicker npk2 = (NumberPicker) findViewById(R.id.npkQuantidade);
        npk2.setMinValue(1);
        npk2.setMaxValue(100);

        npk2 = (NumberPicker) findViewById(R.id.npkDezenas);
        npk2.setMinValue(6);
        npk2.setMaxValue(15);

        RadioButton rdo = (RadioButton)findViewById(R.id.rdoNovaLista);
        rdo.setChecked(true);
        rdo = (RadioButton)findViewById(R.id.rdoAdicionarLista);
        rdo.setEnabled(false);

        Button button = (Button) findViewById(R.id.btnGerar);

        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {

                    RadioGroup rdo = (RadioGroup)findViewById(R.id.rdlLista);

                    RadioButton rdoSelected = (RadioButton)findViewById(rdo.getCheckedRadioButtonId());

                    String selected = String.valueOf(rdoSelected.getText());

                    if(selected.equals("Nova Lista")) {
                        ticketSet = new TicketSet();
                    }

                    NumberPicker npk = (NumberPicker) findViewById(R.id.npkQuantidade);
                    int quantidade = npk.getValue();
                    npk = (NumberPicker) findViewById(R.id.npkDezenas);
                    int dezenas = npk.getValue();

                    for (int i = 1; i <= quantidade; i++) {
                        Gerador.GerarJogo(ticketSet, dezenas);
                    }

                    RadioButton rdoAdicionar = (RadioButton)findViewById(R.id.rdoAdicionarLista);
                    rdoAdicionar.setEnabled(true);

                    lst = (ListView) findViewById(R.id.lsvJogos);

                    lst.setAdapter(CreateAdapter(v, ticketSet));

                    lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, final View v, final int position, long id) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                            builder1.setMessage("Deseja remover este jogo?");
                            builder1.setTitle("Atenção");
                            builder1.setCancelable(true);

                            //final EditText input = new EditText(v.getContext());
                            //builder1.setView(input);

                            builder1.setPositiveButton(
                                    "Sim",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ticketSet.RemoveAt(position);
                                            lst.setAdapter(CreateAdapter(v, ticketSet));

                                            if(ticketSet.Size() == 0){
                                                RadioButton rdo = (RadioButton)findViewById(R.id.rdoNovaLista);
                                                rdo.setChecked(true);
                                                rdo = (RadioButton)findViewById(R.id.rdoAdicionarLista);
                                                rdo.setEnabled(false);
                                            }
                                        }
                                    });

                            builder1.setNegativeButton(
                                    "Não",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            return true;
                        }
                    });
                }
                catch (Exception ex)
                {
                    Toast.makeText(v.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirMenu(View v){
        // Se estive aberto, feche. Senão abra.
        if (mSlidingLayout.isOpen()){
            mSlidingLayout.closePane();
        } else {
            mSlidingLayout.openPane();
        }
    }
//    @Override
//    public void onPanelClosed(View arg0) {
//        // TODO Ao fechar o painel
//    }
//
//    @Override
//    public void onPanelOpened(View arg0) {
//        // TODO Ao abrir o painel
//    }
//
//    @Override
//    public void onPanelSlide(View arg0, float arg1) {
//        // TODO Enquanto o painel desliza
//    }

    private ArrayAdapter<String> CreateAdapter(View v, TicketSet t){

        return new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_list_item_1,
                Gerador.GerarTabelaJogos(t)) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                int fontSize = 20;

                if (ticketSet.GetTicketSize(position) == 15)
                    fontSize = 10;
                else if (ticketSet.GetTicketSize(position) == 14)
                    fontSize = 11;

                else if (ticketSet.GetTicketSize(position) == 13)
                    fontSize = 12;

                else if (ticketSet.GetTicketSize(position) == 12)
                    fontSize = 13;

                else if (ticketSet.GetTicketSize(position) == 11)
                    fontSize = 14;


                else if (ticketSet.GetTicketSize(position) == 10)
                    fontSize = 16;

                else if (ticketSet.GetTicketSize(position) == 9)
                    fontSize = 18;

                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSize);

                // Return the view
                return view;
            }
        };
    }



}


