package scripts.BloodsAirCharger.Work.Utils;

public enum EquipAirStaff {
	AIRSTAFF(1);
	
	private int staffID;
	private boolean haveStaff;
	
	 EquipAirStaff(int staffid) {
		 this.staffID = staffID;
	
	 }
	 
	 public int getItemID() {
		 return this.staffID;
	 }
	 
	 public static EquipAirStaff get(int staffID) {
		 for(EquipAirStaff staff : values()) {
			 if(staff.getItemID() == staffID) {
				 return staff;
			 }
		 }
		return null;
	 }
}