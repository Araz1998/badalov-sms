package com.badalov.sms.badalovsms.model;

import java.io.Serializable;

public record ErrorMessage(String description, String key) implements Serializable {
}
