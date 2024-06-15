package bank_system;
import bank_system.supabase.PostgrestClient;
import bank_system.supabase.SupabaseClient;

import org.json.simple.JSONArray;
import java.util.Comparator;
import org.json.simple.JSONObject;


public class RecTransaction {

    public int id, amount;
    public String date, type;
    
    
    RecTransaction(int id, int amount, String date, String type){
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.type = type;
        }

        public int getId() {
            return id;
        }
    
        public int getAmount() {
            return amount;
        }
    
        public String getDate() {
            return date;
        }
    
        public String getType() {
            return type;
        }
}
