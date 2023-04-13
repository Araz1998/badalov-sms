package com.badalov.sms.badalovsms.model.t2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class T2Response {

    @JsonProperty("ErrorCode")
    public int errorCode;

    @JsonProperty("ErrorMessage")
    public String errorMessage;

    @JsonProperty("Sms")
    public SmsResponseData smsResponseData;

    @JsonProperty("UnAcceptedNumbers")
    public List<String> unAcceptedNumbers;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SmsResponseData {

        @JsonProperty("CharacterType")
        public int characterType;

        @JsonProperty("Characters")
        public int characters;

        @JsonProperty("CreatedDate")
        public String createdDate;

        @JsonProperty("DeliveryRequest")
        public Boolean deliveryRequest;

        @JsonProperty("EmailText")
        public String emailText;

        @JsonProperty("MessageCost")
        public int messageCost;

        @JsonProperty("Number")
        public String number;

        @JsonProperty("PartCost")
        public int partCost;

        @JsonProperty("Parts")
        public int parts;

        @JsonProperty("Priority")
        public int priority;

        @JsonProperty("Text")
        public String text;

        @JsonProperty("UniCode")
        public boolean uniCode;

        @JsonProperty("CampaignId")
        public String campaignId;

        @JsonProperty("CampaignName")
        public String campaignName;

        @JsonProperty("Count")
        public int count;

        @JsonProperty("Direction")
        public int direction;

        @JsonProperty("Flag")
        public int flag;

        @JsonProperty("Id")
        public String id;

        @JsonProperty("MessageFirstName")
        public Object messageFirstName;

        @JsonProperty("MessageLastName")
        public Object messageLastName;

        @JsonProperty("MessageUsername")
        public Object messageUsername;

        @JsonProperty("Recipient")
        public Object recipient;

        @JsonProperty("RecipientsSummary")
        public List<MessageRecipientSummaryData> recipientsSummary;

        @JsonProperty("Sender")
        public String sender;

        @JsonProperty("Status")
        public int status;

        @JsonProperty("Subject")
        public Object subject;

        @JsonProperty("TotalCost")
        public int totalCost;

        @JsonProperty("Type")
        public int type;

        @JsonProperty("UserId")
        public String userId;

        @JsonProperty("VoiceCampaign")
        public List<MessageRecipientSummaryData> voiceCampaign;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageRecipientSummaryData {

        @JsonProperty("Count")
        private int count;

        @JsonProperty("Percentage")
        private int percentage;

        @JsonProperty("Status")
        private int status;

    }

}
