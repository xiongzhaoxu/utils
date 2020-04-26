

package com.sinry.support;

public enum DeviceType {
    PC_WEB("pc_web"),
    MOBILE("mobile"),
    OTHER_TERMINAL("other_terminal");

    String value;

    private DeviceType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static DeviceType fromValue(String text) {
        DeviceType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            DeviceType b = var1[var3];
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return PC_WEB;
    }
}
