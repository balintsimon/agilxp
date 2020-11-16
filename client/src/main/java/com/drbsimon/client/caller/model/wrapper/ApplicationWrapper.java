package com.drbsimon.client.caller.model.wrapper;

import com.drbsimon.client.caller.model.dto.ApplicationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationWrapper {
    List<ApplicationDto> applications;
}
