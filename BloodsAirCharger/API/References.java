package scripts.BloodsAirCharger.API;

import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.BloodsAirCharger.Mode;

/**
 * Created by Andrew on 11/19/2015.
 */
public class References {
	
	private static References ref;
	
	public static References get() {
		return ref == null ? ref = new References() : ref;
		
	}
	
	public static void reset() {
		 ref = null;
	}
	
	public boolean needGuiEntry = true;
	public boolean isUsingAbc;
	public boolean runScript = true;
	public static int altarId = 555;
	public String status;
	public static ABCUtil abc = new ABCUtil();

	
	public boolean deadWalking;
	public String food = "";
	//public static boolean useFood = false;
	public int orbsMade;
	public int initialXp;
	public boolean guiComplete = false;
	public Mode mode;
	public RSItem[] airOrbsInv = Inventory.find("Air orb");
	public RSItem[] unPoweredOrbsInv = Inventory.find("Unpowered orb");

	public boolean useFood = false;
	public int foodToTake;
	public int[] chargedGlorys = {11978,11976,1712,1710,1708,1706};
	public int[] staminaIds = {12631,12629,12627,12625};
	public boolean letsUseStaminas = false;
	public int[] foodIDs = {1965, 1967, 2142, 
			2327, 315, 2140, 3228, 319, 325, 1971, 347, 
			355, 333, 7223, 339, 351, 329, 361, 379, 365, 373, 
			7946, 385, 3971, 391, 2309, 1891, 1893, 1895, 1897, 
			1899, 6701, 7060};
	
	private boolean haveItems  
			= Inventory.find("Cosmic rune").length > 0;
	private boolean haveItems2 = Inventory.find("Unpowered orb").length >0;
	
		
			
	public boolean getCorrectInventory() {
		return haveItems && haveItems2;
	}
	public boolean useStaminas() {
		return letsUseStaminas;
	}
	public boolean getCorrectInventoryFood() {
		return useFood;
	}
}