package scripts.BloodsAirCharger;

import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.types.RSPlayer;
import scripts.BloodsAirCharger.Navigation.Teleport;

/**
 * Created by Andrew on 11/21/2015.
 */
public class PvpThread extends Thread {
	@Override
	public void run() {
		if (isPlayerAttackingMe()){
			Teleport.teleportToEdge();
		}
	}

	public static void main(String args[]) {
		(new PvpThread()).start();
	}

	public static boolean isPlayerAttackingMe() {
		RSPlayer[] players = Players.getAll();
		for (RSPlayer p: players) {
			if (p.isInteractingWithMe() && Player.getRSPlayer().isInCombat()){
				return true;
			}
		}
		return false;
	}
}
