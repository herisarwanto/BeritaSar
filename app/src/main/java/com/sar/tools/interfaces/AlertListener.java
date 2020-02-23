package com.sar.tools.interfaces;

import android.content.DialogInterface;

public interface AlertListener {
    void PositiveMethod(DialogInterface dialog, int id);
    void NegativeMethod(DialogInterface dialog, int id);
}
