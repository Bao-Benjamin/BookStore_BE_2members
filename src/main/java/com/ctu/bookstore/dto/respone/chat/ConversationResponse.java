package com.ctu.bookstore.dto.respone.chat;

import com.ctu.bookstore.entity.chat.ParticipantInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ConversationResponse {
    String id;
    String type;
    String participantsHash;
    String conversationAvatar; // nội suy từ Participants
    String conversationName;
    List<ParticipantInfo> participants;
    Instant createdDate;
    Instant modifiedDate;
}
