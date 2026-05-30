package com.greenmart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenmart.model.ProductSalesReport;
import com.greenmart.repository.ReportRepository;

@Service
public class ReportService {
@Autowired
ReportRepository reportRepository;

/*
 * public List<ProductSalesReport> getProductSalesReport() { return
 * reportRepository.getProductSalesReport(); }
 */
public List<ProductSalesReport> getProductSalesReport(String filter) {
    return reportRepository.getProductSalesReport(filter);
}
}

