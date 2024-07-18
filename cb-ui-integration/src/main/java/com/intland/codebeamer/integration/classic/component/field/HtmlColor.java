package com.intland.codebeamer.integration.classic.component.field;

public enum HtmlColor {
	
	COLOR_8C0F14("#8c0f14"),
	COLOR_D9923B("#d9923b"),
	COLOR_008248("#008248"),
	COLOR_005C50("#005c50"),
	COLOR_007491("#007491"),
	COLOR_454545("#454545"),
	COLOR_B31317("#b31317"),
	COLOR_FFAB46("#ffab46"),
	COLOR_00A85D("#00a85d"),
	COLOR_187A6D("#187a6d"),
	COLOR_0093B8("#0093b8"),
	COLOR_5F5F5F("#5f5f5f"),
	COLOR_CC3F44("#cc3f44"),
	COLOR_FFBC6B("#ffbc6b"),
	COLOR_27C27C("#27c27c"),
	COLOR_3D998D("#3d998d"),
	COLOR_2AB0D1("#2ab0d1"),
	COLOR_858585("#858585"),
	COLOR_E57579("#e57579"),
	COLOR_FFD9AB("#ffd9ab"),
	COLOR_58DBA0("#58dba0"),
	COLOR_6EB8AE("#6eb8ae"),
	COLOR_5ECEEB("#5eceeb"),
	COLOR_ABABAB("#ababab");

	private final String hexCode;

	HtmlColor(String hexCode) {
		this.hexCode = hexCode;
	}

	public String getHexCode() {
		return hexCode;
	}

}
