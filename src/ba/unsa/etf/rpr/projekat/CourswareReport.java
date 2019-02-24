package ba.unsa.etf.rpr.projekat;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.swing.JRViewer;

public class CourswareReport extends JFrame{
    public void showReport(Connection conn) throws JRException {
        String reportSrcFile = getClass().getResource("/reports/courseware.jrxml").getFile();
        String reportsDir = getClass().getResource("/reports/").getFile();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("reportsDirPath", reportsDir);
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);

    }

    public void saveAs(Connection conn, String extension, String path) throws JRException {
        String reportSrcFile = getClass().getResource("/reports/courseware.jrxml").getFile();
        String reportsDir = getClass().getResource("/reports/").getFile();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("reportsDirPath", reportsDir);
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        switch (extension.toUpperCase()) {
            case ".PDF":
                JasperExportManager.exportReportToPdfFile(print, path);
                break;
            case ".DOCX":
                JRDocxExporter exporter = new JRDocxExporter();
                exporter.setExporterInput(new SimpleExporterInput(print));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(path)));
                exporter.exportReport();
                break;
            case ".XML":
                JasperExportManager.exportReportToXmlFile(print, path, false);
                break;
            default:
        }
    }
}
