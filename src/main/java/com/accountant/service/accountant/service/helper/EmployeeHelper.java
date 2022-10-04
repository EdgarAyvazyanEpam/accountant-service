package com.accountant.service.accountant.service.helper;

import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.entity.EmployeeEntity;

import java.util.ArrayList;
import java.util.List;

public class EmployeeHelper {

    public static List<EmployeeEntity> employeeDtosToEmployeeEntities(List<EmployeeDTO> employeeDTOS) {
        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        for (EmployeeDTO dto : employeeDTOS) {
            employeeEntities.add(employeeDtoToEmployeeEntity(dto));
        }
        return employeeEntities;
    }

    public static EmployeeEntity employeeDtoToEmployeeEntity(EmployeeDTO dto) {

        return new EmployeeEntity(dto.getId(), dto.getFullName(),
                dto.getSalary(), dto.getCreationDate(), dto.getFileName(), dto.getFileId());
    }

    public static List<EmployeeDTO> employeeEntitiesToEmployeeDtos(List<EmployeeEntity> entities) {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (EmployeeEntity entity : entities) {
            employeeDTOS.add(employeeEntityToEmployeeDto(entity));
        }
        return employeeDTOS;
    }

    public static EmployeeDTO employeeEntityToEmployeeDto(EmployeeEntity entity) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setSalary(entity.getSalary());
        dto.setCreationDate(entity.getCreationDate());
        dto.setFileName(entity.getFileName());
        dto.setFileId(entity.getFileId());

        return dto;
    }
}
