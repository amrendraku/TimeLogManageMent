package com.example.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Resource;
import com.example.repository.ResourceRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public void saveResourcesFromExcel(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
            Iterator<Row> rowIterator = sheet.iterator();

            List<Resource> resources = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }
                Resource resource = new Resource();
                resource.setResourceId((long) row.getCell(0).getNumericCellValue());
                resource.setResourceName(getStringCellValue(row.getCell(1)));
                resource.setResourceType(getStringCellValue(row.getCell(2)));
                resource.setResourceDesignation(getStringCellValue(row.getCell(3)));
                resource.setResourceJoiningDate(getDateCellValue(row.getCell(4)));
                resource.setResourceSeparationDate(getDateCellValue(row.getCell(5)));
                resource.setResourceManagerName(getStringCellValue(row.getCell(6)));
                resource.setCreatedBy(getStringCellValue(row.getCell(7)));
                resource.setCreationDate(getDateCellValue(row.getCell(8)));
                resource.setUpdatedBy(getStringCellValue(row.getCell(9)));
                resource.setUpdationDate(getDateCellValue(row.getCell(10)));
                resource.setVersion((long) row.getCell(11).getNumericCellValue());
                Long isActiveValue = getNumericCellValue(row.getCell(12));
                resource.setIsActive(isActiveValue != null ? isActiveValue : 0L);



                resources.add(resource);
            }

            // Save resources to the database
            resourceRepository.saveAll(resources);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error parsing Excel file: " + e.getMessage());
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else {
            return null;
        }
    }

    private Date getDateCellValue(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }

        if (DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue();
        } else {
            return null;
        }
    }
    
    
    private Long getNumericCellValue(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }
        return (long) cell.getNumericCellValue();
    }




}
