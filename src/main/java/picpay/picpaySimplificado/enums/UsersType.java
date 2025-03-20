package picpay.picpaySimplificado.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UsersType {
    MERCHANT("M", "Merchant"),
    COMMON("C", "Common");

    private final String code;
    private final String description;

    UsersType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static UsersType fromCode(String code) {
        for (UsersType type : UsersType.values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de usuário inválido: " + code);
    }
}