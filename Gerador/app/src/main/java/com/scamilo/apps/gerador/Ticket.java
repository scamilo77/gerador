package com.scamilo.apps.gerador;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scamilo on 2/1/2016.
 */
public class Ticket
{
private List<Integer> Values;

public Ticket()
        {
        Values = new ArrayList<Integer>();
        }

public void Add(int value)
        {
        Values.add(value);
        }

public int Size()
        {
        return Values.size();
        }

public List<Integer> GetValues()
        {
            return Values;
        }
}