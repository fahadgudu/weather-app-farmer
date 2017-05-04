package com.dapperapps.ciandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreference {

	private static final String TAG = "AppPreference";
	public static final String SESSION_NAME = "CIAndroid";
	private static SharedPreferences usrPrefrence = null;

	private static void initPrefrence(Context mContext) {

		try {
			AppPreference.usrPrefrence = mContext.getSharedPreferences(
					SESSION_NAME, Context.MODE_PRIVATE);
		} catch (final Exception exception) {
		}
	}

	public static void saveNum(Context context, long value, String key) {

		try {
			if (AppPreference.usrPrefrence == null) {
				AppPreference.initPrefrence(context);
			}
			if (AppPreference.usrPrefrence != null) {
				final Editor editing = AppPreference.usrPrefrence.edit();
				try {
					editing.remove(key);
				} catch (final Exception exception) {
				}
				editing.putLong(key, value);
				editing.commit();
			}
		} catch (final Exception exception) {
		}
	}

	public static long getNum(Context context, String key) {

		try {
			if (AppPreference.usrPrefrence == null) {
				AppPreference.initPrefrence(context);
			}
			return AppPreference.usrPrefrence.getLong(key, -1);
		} catch (final Exception exception) {
		}
		return -1;
	}

	public static void saveInt(Context context, int value, String key) {

		try {
			if (AppPreference.usrPrefrence == null) {
				AppPreference.initPrefrence(context);
			}
			if (AppPreference.usrPrefrence != null) {
				final Editor editing = AppPreference.usrPrefrence.edit();
				try {
					editing.remove(key);
				} catch (final Exception exception) {
				}
				editing.putInt(key, value);
				editing.commit();
			}
		} catch (final Exception exception) {
		}
	}

	public static int getInt(Context context, String key) {

		try {
			if (AppPreference.usrPrefrence == null) {
				AppPreference.initPrefrence(context);
			}
			return AppPreference.usrPrefrence.getInt(key, -1);
		} catch (final Exception exception) {
		}
		return -1;
	}

	public static void saveValue(Context context, String value, String key) {

		try {
			if (AppPreference.usrPrefrence == null) {
				AppPreference.initPrefrence(context);
			}
			if (AppPreference.usrPrefrence != null) {
				final Editor editing = AppPreference.usrPrefrence.edit();
				try {
					editing.remove(key);
				} catch (final Exception exception) {
				}
				editing.putString(key, value);
				editing.commit();
			}
		} catch (final Exception exception) {
		}
	}

	public static String getValue(Context context, String key) {

		try {
			if (AppPreference.usrPrefrence == null) {
				AppPreference.initPrefrence(context);
			}
			return AppPreference.usrPrefrence.getString(key, null);
		} catch (final Exception exception) {
		}
		return null;
	}

	public static void saveData(Context context, boolean values, String key) {

		try {
			if (AppPreference.usrPrefrence == null) {
				AppPreference.initPrefrence(context);
			}
			if (AppPreference.usrPrefrence != null) {
				final Editor editing = AppPreference.usrPrefrence.edit();
				try {
					editing.remove(key);
				} catch (final Exception exception) {
				}
				editing.putBoolean(key, values);
				editing.commit();
			}
		} catch (final Exception exception) {
		}
	}

	public static boolean getSavedData(Context context, String key) {

		boolean flag = false;
		try {
			if (AppPreference.usrPrefrence == null) {
				AppPreference.initPrefrence(context);
			}
			if (AppPreference.usrPrefrence != null) {
				flag = AppPreference.usrPrefrence.getBoolean(key, false);
			}
		} catch (final Exception exception) {
		}
		return flag;
	}

	public static boolean DeleteSavedData(Context context) {

		try {
			if (AppPreference.usrPrefrence == null) {
				return true;
			}
			if (AppPreference.usrPrefrence != null) {
				AppPreference.usrPrefrence.edit().clear();
				return true;
			}
		} catch (final Exception exception) {
			return false;
		}
		return true;
	}

	public void clearMemory(Context context) {
		if (AppPreference.usrPrefrence == null) {
			AppPreference.initPrefrence(context);
		}

		try {
			if (AppPreference.usrPrefrence != null) {

				Editor editor = AppPreference.usrPrefrence.edit();
				editor.clear();
				editor.commit();
			}
		} catch (Exception exception) {
		}
	}

}
