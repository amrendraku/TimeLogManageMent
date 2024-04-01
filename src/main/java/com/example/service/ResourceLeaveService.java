package com.example.service;

import com.example.entity.Resource;
import com.example.entity.ResourceLeave;
import com.example.repository.ResourceLeaveRepository;
import com.example.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceLeaveService {

    @Autowired
    private ResourceLeaveRepository resourceLeaveRepository;
    
    @Autowired
    private ResourceRepository resourceRepository;

    public void saveResourcesFromExcel(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            List<ResourceLeave> resourceLeaves = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) { 
                    continue;
                }
                ResourceLeave resourceLeave = new ResourceLeave();
                resourceLeave.setResourceLeaveId(Long.parseLong(getStringCellValue(row.getCell(0))));
                
                resourceLeave.setLeaveMonth(getStringCellValue(row.getCell(1)));
                resourceLeave.setLeaveType(getStringCellValue(row.getCell(2)));
                // Assuming resource_id is in the third column, adjust it accordingly
                
                Optional<Resource> optionalResource = resourceRepository.findById(Long.parseLong(getStringCellValue(row.getCell(3))));
                if (optionalResource.isPresent()) {
                    Resource resource = optionalResource.get();
                    resourceLeave.setResource(resource);
                } else {
                    // Throw custom exception if resource is not found
                    throw new ResourceNotFoundException("Resource with ID " + getStringCellValue(row.getCell(3)) + " not found");
                }
//                resourceLeave.setResource(resourceRepository.findById(Long.parseLong(getStringCellValue(row.getCell(2)))));
                
                
                resourceLeave.setNoOfDays((long) row.getCell(4).getNumericCellValue());
                resourceLeave.setLeaveStartDate(getDateCellValue(row.getCell(5)));
                resourceLeave.setLeaveEndDate(getDateCellValue(row.getCell(6)));
                resourceLeave.setCreatedBy(getStringCellValue(row.getCell(7)));
                resourceLeave.setCreationDate(getDateCellValue(row.getCell(8)));
                resourceLeave.setUpdatedBy(getStringCellValue(row.getCell(9)));
                resourceLeave.setUpdationDate(getDateCellValue(row.getCell(10)));
                resourceLeave.setVersion((long) row.getCell(11).getNumericCellValue());
             // Assuming the isActive field in ResourceLeave is Long
                Long isActiveValue = getNumericCellValue(row.getCell(12));
                resourceLeave.setIsActive(isActiveValue != null ? isActiveValue : 0L);


                resourceLeaves.add(resourceLeave);
            }

            // Save resource leaves to the database
            resourceLeaveRepository.saveAll(resourceLeaves);
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            // Check if the numeric cell contains a date
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toString(); // Return the date as a string
            } else {
                // Return the numeric value as a string
                return String.valueOf((long) cell.getNumericCellValue());
            }
        } else {
            return null; // Return null for other cell types
        }
    }



    private Date getDateCellValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.STRING) {
            return null;
        }
        return cell.getDateCellValue();
    }
    
    private Long getNumericCellValue(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }
        return (long) cell.getNumericCellValue();
    }

    
    public class ResourceNotFoundException extends RuntimeException {

        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

}
