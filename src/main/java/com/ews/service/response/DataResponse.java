package com.ews.service.response;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DataResponse<T> {

    int statusCode;
    String message;
    T data;

}
