package bank_system;

// import java.io.IOException;
import java.math.BigDecimal;
// import java.net.URI;
// import java.net.URISyntaxException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yaphet.chapa.Chapa;
import com.yaphet.chapa.model.InitializeResponseData;
import com.yaphet.chapa.model.PostData;
import com.yaphet.chapa.model.VerifyResponseData;
import com.yaphet.chapa.utility.Util;



public class ChapaConnection /*extends Application */{

    public static String checkout_url;
    private static String tx_id;

	// Enter your api key here
    public static JsonObject payment(String email,String fname,String lname,String amount){
		String key_api = "CHASECK_TEST-F9MVEeIdjEy2gkZSJE3LmTd26MkT680E";
		Chapa chapa = new Chapa(key_api);

		String tx_ref = Util.generateToken();

		PostData postData = new PostData()
				.setAmount(new BigDecimal(amount))
				.setCurrency("ETB")
				.setFirstName(fname)
				.setLastName(lname)
				.setEmail(email)
				.setTxRef(tx_ref)
				//.setCallbackUrl("https://chapa.co")
				//.setReturnUrl("https://chapa.co")
				.setSubAccountId("testSubAccountId");

		JsonObject file = new JsonObject();

		try {
			InitializeResponseData responseData = chapa.initialize(postData);
			Gson gson = new Gson();
			JsonElement jsonElement = gson.toJsonTree(responseData);
			file.add("detail", jsonElement);
			file.addProperty("tx_id", tx_ref);
			//System.out.println(file);
			return file;
			
		} catch (Throwable e) {

			e.printStackTrace();
			return null;
		}
	}

	// Enter your api key here
	public static String verification(String tx_id){ 
		String key_api = "CHASECK_TEST-F9MVEeIdjEy2gkZSJE3LmTd26MkT680E";
		Chapa chapa = new Chapa(key_api);
		
		try {
			VerifyResponseData verifyResponseData = chapa.verify(tx_id);
			// System.out.println(verifyResponseData.getMessage());
			// System.out.println(verifyResponseData.getRawJson());
			return verifyResponseData.getRawJson();
	
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

    public static String openCheckoutUrl() {
        return checkout_url;
    }

}
