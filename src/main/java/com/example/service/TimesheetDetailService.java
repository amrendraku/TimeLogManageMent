package com.example.service;

import com.example.entity.Resource;
import com.example.entity.TimesheetDetail;
import com.example.repository.TimesheetDetailRepository;
import com.example.service.ResourceLeaveService.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

import com.example.repository.ResourceRepository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TimesheetDetailService {

    @Autowired
    private TimesheetDetailRepository timesheetDetailRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    public void saveTimesheetDetailsFromExcel(MultipartFile file, String defaultMonth) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            List<TimesheetDetail> timesheetDetails = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) { 
                    continue;
                }
                TimesheetDetail timesheetDetail = new TimesheetDetail();
                timesheetDetail.setTimesheetDetailId(Long.parseLong(getStringCellValue(row.getCell(0))));
                
                
//                timesheetDetail.setTimesheetDetailMonth(getStringCellValue(row.getCell(1)));
                
                String month = getStringCellValue(row.getCell(1));
                if (month == null || month.isEmpty()) {
                    month = defaultMonth;
                }
                timesheetDetail.setTimesheetDetailMonth(month);
                
                
                timesheetDetail.setTemplateNameId(Long.parseLong(getStringCellValue(row.getCell(2))));
                
               
                Optional<Resource> optionalResource = resourceRepository.findById(Long.parseLong(getStringCellValue(row.getCell(3))));
                if (optionalResource.isPresent()) {
                	System.out.println("sdfghj"+optionalResource.get());
                    Resource resource = optionalResource.get();
                    timesheetDetail.setResource(resource);
                } 
                else {
                    throw new ResourceNotFoundException("Resource with ID " + getStringCellValue(row.getCell(3)) + " not found in resource table");
                }
                
//                try {
//                    Resource resource = resourceRepository.getById(Long.parseLong(getStringCellValue(row.getCell(3))));
//                    timesheetDetail.setResource(resource);
//                } catch (EntityNotFoundException e) {
//                    throw new ResourceNotFoundException("Resource with ID " + getStringCellValue(row.getCell(3)) + " not found in resource table");
//                }

                
                
//                timesheetDetail.setResource(Long.parseLong(getStringCellValue(row.getCell(3))));
                
                
                timesheetDetail.setJobName(getStringCellValue(row.getCell(4)));
                timesheetDetail.setBillableStatus(Long.parseLong(getStringCellValue(row.getCell(5))));
                timesheetDetail.setBillableHour(Long.parseLong(getStringCellValue(row.getCell(6))));
                timesheetDetail.setCreatedBy(getStringCellValue(row.getCell(7)));
                timesheetDetail.setCreationDate(getDateCellValue(row.getCell(8)));
                timesheetDetail.setUpdatedBy(getStringCellValue(row.getCell(9)));
                timesheetDetail.setUpdationDate(getDateCellValue(row.getCell(10)));
                timesheetDetail.setVersion(Long.parseLong(getStringCellValue(row.getCell(11))));
                timesheetDetail.setIsActive(Long.parseLong(getStringCellValue(row.getCell(12))));

                timesheetDetails.add(timesheetDetail);
            }
            timesheetDetailRepository.saveAll(timesheetDetails);
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            // Convert numeric value to string
            return String.valueOf((long) cell.getNumericCellValue());
        } else {
            return null;
        }
    }


    private Date getDateCellValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.STRING) {
            return null;
        }
        return cell.getDateCellValue();
    }
    
    public class ResourceNotFoundException extends RuntimeException {

        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
