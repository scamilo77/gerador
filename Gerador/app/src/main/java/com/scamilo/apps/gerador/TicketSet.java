package com.scamilo.apps.gerador;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scamilo on 2/1/2016.
 */
public class TicketSet {
    private List<Ticket> Tickets;

    public TicketSet()
    {
        Tickets = new ArrayList<Ticket>();
    }

    public void Add(Ticket ticket)
    {
        Tickets.add(ticket);
    }

    public List<Ticket> GetList()
    {
        return Tickets;
    }

    public int Size()
    {
        return Tickets.size();
    }

    public int GetTicketSize(int index)
    {
        return Tickets.get(index).Size();
    }

    public int MaximumLength()
    {
        //var firstOrDefault = Tickets.FirstOrDefault(x => x.Size() <= 15);
        //return firstOrDefault?.Size() ?? 0;

        int max = 0;

        for (Ticket ticket : Tickets)
        {
            if (ticket.Size() > max)
                max = ticket.Size();
        }

        return max;

        //return Tickets.Select(ticket => ticket.Size()).Concat(new[] { 0 }).Max();
    }

    public void RemoveAt(int index)
    {
        Tickets.remove(index);
    }

    public boolean Exists(Ticket ticket)
    {
       return Utils.Exists(ticket.GetValues(), Tickets);
    }
}
