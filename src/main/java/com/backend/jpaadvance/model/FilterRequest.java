package com.backend.jpaadvance.model;

import com.backend.jpaadvance.enumeration.FieldType;
import com.backend.jpaadvance.enumeration.Operator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterRequest {

    private String key;
    private Operator operator;
    @JsonIgnore
    private FieldType fieldType;
    private Object value;
    private Object valueTo;
    private List<Object> values;
}
