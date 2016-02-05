package com.scamilo.apps.gerador;

import com.scamilo.apps.gerador.Ticket;
import com.scamilo.apps.gerador.TicketSet;

import java.util.List;
import java.util.Random;

/**
 * Created by scamilo on 2/1/2016.
 */
public class Gerador {

    public static void GerarJogo(TicketSet tickets, int quantidadeDezenas)throws Exception{

        while (true)
        {
            //Novo jogo
            Ticket ticket = new Ticket();

            //Contagem das dezenas
            int contagem = 0;

            //Sorteio
            while (contagem < quantidadeDezenas)
            {
                //Novo sorteio de dezena
                int dezena = RandomWithRange(1, 60);

                if (ticket.GetValues().contains(dezena))
                {
                    continue;
                }
//                if (DezenasFiltro.Contains(dezena))
//                {
//                    continue;
//                }
                //Adiciona a dezena

                ticket.Add(dezena);

                //Acrescenta o contador
                contagem++;

            }

            if (tickets.Exists(ticket))
            {
                continue;
            }

            tickets.Add(ticket);
            break;
        }
    }

    public static String[] GerarTabelaJogos(TicketSet tickets){

        String[] jogos = new String[tickets.GetList().size()];

        int count = 0;
        for(Ticket ticket : tickets.GetList()) {

            String value = "";

            Integer[] lista = Utils.Sort(ticket.GetValues());

            for(Integer item : lista) {

                if (String.valueOf(item).length() == 1)
                    value += "0" + item + " - ";
                else
                    value += item + " - ";

            }
            jogos[count] = value.substring(0, value.length() - 2);
            count++;
        }
        return jogos;
    }

    private static int RandomWithRange(int min, int max){
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (min <= max ? min : max);
    }
}


