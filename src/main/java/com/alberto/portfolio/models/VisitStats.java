package com.alberto.portfolio.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "VisitStats")
public class VisitStats {
    @Id
    private String id; // use a singleton id, e.g. "VISITS"

    private long total;
    private Instant lastVisitAt;
    private Instant createdAt;
}
