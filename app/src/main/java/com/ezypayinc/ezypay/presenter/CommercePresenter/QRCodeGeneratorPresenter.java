package com.ezypayinc.ezypay.presenter.CommercePresenter;


import android.graphics.Bitmap;
import android.graphics.Color;

import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.interfaceViews.IQRCodeGeneratorView;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;


public class QRCodeGeneratorPresenter implements IQRCodeGeneratorPresenter {
    private IQRCodeGeneratorView mView;

    public QRCodeGeneratorPresenter(IQRCodeGeneratorView view) {
        mView = view;
    }


    @Override
    public void generateQrCode(Payment payment) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            String jsonContent = getPaymentJsonString(payment);
            BitMatrix bitMatrix = qrCodeWriter.encode(jsonContent, BarcodeFormat.QR_CODE, 300, 300);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            mView.showQRImage(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getPaymentJsonString(Payment payment) throws JSONException {
        JSONObject jsonObject = payment.toJSON();
        if(jsonObject.has("currency")) {
            JSONObject currency = jsonObject.getJSONObject("currency");
            jsonObject.remove("currency");
            jsonObject.put("Currency", currency);
        }
        User user = UserSingleton.getInstance().getUser();
        if (user.getUserType() == 4) {
            JSONObject commerce = getCommerceJSON(user.getEmployeeBoss());
            jsonObject.put("Commerce", commerce);
        } else {
            JSONObject commerce = getCommerceJSON(user);
            jsonObject.put("Commerce", commerce);
        }
        return  jsonObject.toString();
    }

    private JSONObject getCommerceJSON(User commerce) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", commerce.getId());
        jsonObject.put("name", commerce.getName());
        jsonObject.put("avatar", commerce.getAvatar());
        jsonObject.put("userType", commerce.getUserType());
        return jsonObject;
    }
}
