package models;

import java.security.MessageDigest;
import java.util.HashMap;

/**
 * Użycie na własną odpowiedzialność / Use on your own reponsibility ;)
 * Created by Michal Zubkowicz (michal.zubkowicz@gmail.com).
 */
public class Dotpay {
    public static String PLN="PLN";
    public static String DOTPAY_IP="195.150.9.37";
    private HashMap<String,String> req;
    private Integer id;
    private String operation_number;
    private OperationType operation_type;
    private OperationStatus operation_status;
    private String operation_amount;
    private String operation_currency;
    private String operation_withdrawal_amount;
    private String operation_commission_amount;
    private String operation_original_amount;
    private String operation_original_currency;
    private String operation_datetime;
    private String operation_related_number;
    private String control;
    private String description;
    private String email;
    private String p_info;
    private String p_email;
    private String channel;
    private String channel_country;
    private String geoip_country;
    private String signature;

    public Dotpay(HashMap<String,String> req) throws Exception {
        this.req=req;
        try {
            this.id = Integer.valueOf(req.get("id"));
        } catch(NullPointerException e) {
            throw new Exception("Brak pola ID ");
        }

        try {
            this.operation_number = req.get("operation_number");
        } catch(NullPointerException e) {
            throw new Exception("Brak pola operation_number");
        }

        try {
            this.operation_type = OperationType.valueOf(req.get("operation_type").toUpperCase());
        } catch(NullPointerException e) {
            throw new Exception("Brak pola operation_type ");
        }

        try {
            this.operation_status = OperationStatus.valueOf(req.get("operation_status").toUpperCase());
        } catch(NullPointerException e) {
            throw new Exception("Brak pola operation_status ");
        }

        try {
            this.operation_amount = req.get("operation_amount");
        } catch(NullPointerException e) {
            throw new Exception("Brak pola operation_amount ");
        }

        try {
            this.operation_currency = req.get("operation_currency");
        } catch(NullPointerException e) {
            throw new Exception("Brak pola operation_currency ");
        }

        try {
            this.control = req.get("control");
        } catch(NullPointerException e) {
            throw new Exception("Brak pola control ");
        }


        try {
            this.signature = req.get("signature");
        } catch(NullPointerException e) {
            throw new Exception("Brak pola signature ");
        }

        try {
            this.p_email=req.get("p_email");
        } catch(NullPointerException e) {
            throw new Exception("Brak pola p_email ");
        }

        this.operation_withdrawal_amount=req.getOrDefault("operation_withdrawal_amount", "");
        this.operation_commission_amount=req.getOrDefault("operation_commission_amount", "");
        this.operation_original_amount=req.getOrDefault("operation_original_amount", "");
        this.operation_original_currency=req.getOrDefault("operation_original_currency", "");
        this.operation_datetime = req.getOrDefault("operation_datetime", "");
        this.operation_related_number = req.getOrDefault("operation_related_number","");
        this.description=req.getOrDefault("description", "");
        this.email=req.getOrDefault("email", "");
        this.p_info=req.getOrDefault("p_info", "");

        this.channel=req.getOrDefault("channel", "");
        this.channel_country = req.getOrDefault("channel_country", "");
        this.geoip_country=req.get("geoip_country");

    }

    public String generateSignature(String pin) {
        String[] fields={"id","operation_number","operation_type","operation_status","operation_amount",
                "operation_currency","operation_withdrawal_amount","operation_commission_amount",
                "operation_original_amount","operation_original_currency","operation_datetime",
                "operation_related_number","control","description",
                "email","p_info","p_email","channel","channel_country","geoip_country"};
        StringBuilder sb = new StringBuilder(pin);
        for (String field : fields) sb.append(this.req.getOrDefault(field, ""));

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(sb.toString().getBytes());
            byte byteData[] = md.digest();
            sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }

            //hashowanie zgodne z PHP
            sb = new StringBuilder();
            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) sb.append('0');
                sb.append(hex);
            }

           return sb.toString();
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public boolean checkSignature(String pin) {
        return signature.equals(generateSignature(pin));
    }

    public static boolean checkIP(String ip) {
        return ip.equals(DOTPAY_IP);
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperation_number() {
        return operation_number;
    }

    public void setOperation_number(String operation_number) {
        this.operation_number = operation_number;
    }

    public OperationType getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(OperationType operation_type) {
        this.operation_type = operation_type;
    }

    public OperationStatus getOperation_status() {
        return operation_status;
    }

    public void setOperation_status(OperationStatus operation_status) {
        this.operation_status = operation_status;
    }

    public String getOperation_amount() {
        return operation_amount;
    }

    public void setOperation_amount(String operation_amount) {
        this.operation_amount = operation_amount;
    }

    public String getOperation_currency() {
        return operation_currency;
    }

    public void setOperation_currency(String operation_currency) {
        this.operation_currency = operation_currency;
    }

    public String getOperation_withdrawal_amount() {
        return operation_withdrawal_amount;
    }

    public void setOperation_withdrawal_amount(String operation_withdrawal_amount) {
        this.operation_withdrawal_amount = operation_withdrawal_amount;
    }

    public String getOperation_commission_amount() {
        return operation_commission_amount;
    }

    public void setOperation_commission_amount(String operation_commission_amount) {
        this.operation_commission_amount = operation_commission_amount;
    }

    public String getOperation_original_amount() {
        return operation_original_amount;
    }

    public void setOperation_original_amount(String operation_original_amount) {
        this.operation_original_amount = operation_original_amount;
    }

    public String getOperation_original_currency() {
        return operation_original_currency;
    }

    public void setOperation_original_currency(String operation_original_currency) {
        this.operation_original_currency = operation_original_currency;
    }

    public String getOperation_datetime() {
        return operation_datetime;
    }

    public void setOperation_datetime(String operation_datetime) {
        this.operation_datetime = operation_datetime;
    }

    public String getOperation_related_number() {
        return operation_related_number;
    }

    public void setOperation_related_number(String operation_related_number) {
        this.operation_related_number = operation_related_number;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getP_info() {
        return p_info;
    }

    public void setP_info(String p_info) {
        this.p_info = p_info;
    }

    public String getP_email() {
        return p_email;
    }

    public void setP_email(String p_email) {
        this.p_email = p_email;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel_country() {
        return channel_country;
    }

    public void setChannel_country(String channel_country) {
        this.channel_country = channel_country;
    }

    public String getGeoip_country() {
        return geoip_country;
    }

    public void setGeoip_country(String geoip_country) {
        this.geoip_country = geoip_country;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public enum OperationStatus {
        NEW,PROCESSING,COMPLETED,REJECTED,PROCESSING_REALIZATION_WAITING,PROCESSING_REALIZATION
    }

    public enum OperationType {
        PAYMENT,PAYMENT_MULTIMERCHANT_CHILD,PAYMENT_MULTIMERCHANT_PARENT,REFUND,PAYOUT,RELEASE_ROLLBACK,UNIDENTIFIED_PAYMENT,COMPLAINT
    }

}
