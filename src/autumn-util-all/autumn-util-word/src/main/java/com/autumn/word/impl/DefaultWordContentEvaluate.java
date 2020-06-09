package com.autumn.word.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.autumn.word.*;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import com.autumn.evaluator.Variant;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.tuple.TupleTwo;

/**
 * Word 默认内容解析
 *
 * @author 老码农 2019-04-20 13:38:05
 */
public class DefaultWordContentEvaluate extends AbstractWordContentEvaluate {

    /**
     *
     */
    public DefaultWordContentEvaluate() {

    }

    @Override
    public void evaluateGeneralParagraph(WordSession session, XWPFParagraph para) {
        this.evaluateParagraph(session, para, session.getToken().getGeneralBeginMark(), session.getToken().getGeneralEndMark());
    }

    /**
     * 解析 XWPFParagraph
     *
     * @param para
     * @param beginMark
     * @param endMark
     */
    private void evaluateParagraph(WordSession session, XWPFParagraph para, String beginMark, String endMark) {
        XWPFUtils.evaluateParagraph(para, beginMark, endMark, (token) -> evaluateParagraphRun(session, token));
    }

    /**
     * @param session 会话
     * @param token   标记
     * @return
     */
    private boolean evaluateParagraphRun(WordSession session, EvaluateParagraphToken token) {
        EvaluateExpressionResult result = this.createEvaluateExpressionResult(token);
        if (result == null) {
            return false;
        }
        Variant value = this.evaluateValue(session, result.getEvlExpression(), token.getBeginMark(), token.getEndMark());
        if (token.getBeginIndex() == token.getEndIndex()) {
            if (value.isNull()) {
                this.insertCopyRun(token.getPara(), token.getRuns(), token.getBeginIndex(), result.getBeginRunText() + result.getEndRunText());
            } else {
                this.insertCopyRun(token.getPara(), token.getRuns(), token.getBeginIndex(), result.getBeginRunText() + value.toString(false) + result.getEndRunText());
            }
        } else {
            if (token.getEndIndex() - token.getBeginIndex() == 1) {
                if (value.isNull()) {
                    this.insertCopyRun(token.getPara(), token.getRuns(), token.getBeginIndex(), result.getBeginRunText());
                } else {
                    this.insertCopyRun(token.getPara(), token.getRuns(), token.getBeginIndex(), result.getBeginRunText() + value.toString(false));
                }
            } else {
                this.insertCopyRun(token.getPara(), token.getRuns(), token.getBeginIndex(), result.getBeginRunText());
                boolean isBegin = false;
                for (int i = token.getBeginIndex() + 1; i < token.getEndIndex(); i++) {
                    XWPFRun run = token.getRuns().get(i);
                    if (!StringUtils.isNullOrBlank(run.toString())) {
                        if (!isBegin) {
                            if (value.isNull()) {
                                this.insertCopyRun(token.getPara(), token.getRuns(), i, "");
                            } else {
                                this.insertCopyRun(token.getPara(), token.getRuns(), i, value.toString(false));
                            }
                            isBegin = true;
                        } else {
                            this.insertCopyRun(token.getPara(), token.getRuns(), i, "");
                        }
                    }
                }
            }
            this.insertCopyRun(token.getPara(), token.getRuns(), token.getEndIndex(), result.getEndRunText());
        }
        return true;
    }

    @Override
    public void evaluateTable(WordSession session, XWPFTable table) {
        TableExpressionType type = this.getTableExpressionType(session, table);
        if (type.getExpressionType().equals(TableExpressionType.ExpressionType.GENERAL)
                || type.getExpressionType().equals(TableExpressionType.ExpressionType.GENERAL_DYNAMIC)) {
            for (XWPFTableCell cell : type.getGeneralCells()) {
                List<XWPFParagraph> paragraphs = cell.getParagraphs();
                for (XWPFParagraph para : paragraphs) {
                    this.evaluateGeneralParagraph(session, para);
                }
            }
        }
        if (type.getExpressionType().equals(TableExpressionType.ExpressionType.DYNAMIC)
                || type.getExpressionType().equals(TableExpressionType.ExpressionType.GENERAL_DYNAMIC)) {
            this.evaluateDynamicTable(session, table, type);
        }
    }

    /**
     * 解析动态表格
     *
     * @param session 会话
     * @param table   表格
     * @param type    类型
     */
    private void evaluateDynamicTable(WordSession session, XWPFTable table, TableExpressionType type) {
        Variant array = session.getContext().getVariable(type.getDynamicMemberName());
        this.clearDynamicAll(type);
        if (array == null || array.isNull()) {
            return;
        }
        if (!array.isArray()) {
            throw new WordEvaluateException(
                    String.format("表格表达式集合表达式中数组变量  %s 对应的值不是数组类型。", type.getDynamicMemberName()));
        }
        Variant[] arrays = (Variant[]) array.getValue();
        if (arrays.length == 0) {
            return;
        }
        Variant[] itemArray = (Variant[]) arrays[0].getValue();
        int rowCount = itemArray.length;
        if (rowCount == 0) {
            return;
        }
        for (int i = 0; i < rowCount - 1; i++) {
            XWPFTableRow newRow = table.insertNewTableRow(type.getBeginRow());
            if (newRow != null) {
                copyRowPro(type.getFunctionFow(), newRow);
            }
        }
        int rowBeginRow = type.getBeginRow();
        int rowIndex = rowBeginRow;
        List<XWPFTableRow> rows = table.getRows();
        Map<Integer, Map<Integer, TupleTwo<String, Boolean>>> rowMap = new HashMap<>();
        for (int i = 0; i < itemArray.length; i++) {
            HashMap<String, Variant> tempArray = this.rowVariants(arrays, i);
            Map<Integer, TupleTwo<String, Boolean>> colMap = rowMap.get(rowIndex);
            if (colMap == null) {
                colMap = new HashMap<>();
                rowMap.put(rowIndex, colMap);
            }
            if (tempArray.size() > 0) {
                session.getContext().backup(); // 备份变量
                // 切换工作变量到当前数组行
                for (String key : tempArray.keySet()) {
                    session.getContext().setVariable(key, tempArray.get(key));
                }
                try {
                    XWPFTableRow row = rows.get(rowIndex);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (TableDynamicCellExcpression cellExcpression : type.getDynamicCells()) {
                        this.evaluateDynamicCell(session, rows, row, cells, cellExcpression, rowMap, colMap, rowIndex,
                                rowBeginRow);
                    }
                } finally {
                    session.getContext().revert(); // 恢复变量
                }
            }
            rowIndex++;
        }
        rowMap.clear();
    }

    /**
     * 解析动态工作单元
     *
     * @param session         会话
     * @param rows
     * @param row
     * @param cells
     * @param cellExcpression
     * @param rowMap
     * @param colMap
     * @param rowIndex
     * @param rowBeginRow
     */
    private void evaluateDynamicCell(WordSession session, List<XWPFTableRow> rows, XWPFTableRow row, List<XWPFTableCell> cells,
                                     TableDynamicCellExcpression cellExcpression, Map<Integer, Map<Integer, TupleTwo<String, Boolean>>> rowMap,
                                     Map<Integer, TupleTwo<String, Boolean>> colMap, int rowIndex, int rowBeginRow) {
        Variant value = this.evaluateValue(session, cellExcpression.getExcpression(), session.getToken().getCollectionBeginMark(), session.getToken().getCollectionEndMark());
        int colIndex = cellExcpression.getColIndex();
        XWPFTableCell cell = cells.get(colIndex);
        String cellValue;
        if (value.isNull()) {
            cellValue = "";
        } else {
            cellValue = value.toString(false);
        }
        boolean isFirstValue = true;
        if (cellExcpression.isMergeRow()) {
            if (rowIndex > rowBeginRow) {
                int previousRow = rowIndex - 1;
                TupleTwo<String, Boolean> previousValue = rowMap.get(previousRow).get(colIndex);
                if (cellValue.equals(previousValue.getItem1())) {
                    if (previousValue.getItem2()) {
                        XWPFTableCell previousCell = rows.get(previousRow).getCell(colIndex);
                        previousCell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
                    }
                    cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                    this.clearCellParagraph(cell);
                    isFirstValue = false;
                }
            }
            colMap.put(colIndex, new TupleTwo<>(cellValue, isFirstValue));
        }
        if (isFirstValue) {
            this.setCellText(cell, cellValue);
        }
    }

    /**
     * @param cell
     */
    private void clearCellParagraph(XWPFTableCell cell) {
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            int size = runs.size();
            while (size > 0) {
                paragraph.removeRun(0);
                size--;
            }
        }
    }

    private HashMap<String, Variant> rowVariants(Variant[] arrayArray, int arrayRowIndex) {
        HashMap<String, Variant> items = new HashMap<>();
        for (Variant array : arrayArray) {
            if (array.isArray()) {
                Variant[] itemArray = (Variant[]) array.getValue();
                if (itemArray.length > arrayRowIndex) {
                    items.put(array.getName(), itemArray[arrayRowIndex]);
                }
            }
        }
        return items;
    }

    /**
     * 复制行
     *
     * @param sourceRow
     * @param targetRow
     */
    private void copyRowPro(XWPFTableRow sourceRow, XWPFTableRow targetRow) {
        // 复制行属性
        targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
        List<XWPFTableCell> cellList = sourceRow.getTableCells();
        if (null == cellList) {
            return;
        }
        // 添加列、复制列以及列中段落属性
        XWPFTableCell targetCell;
        for (XWPFTableCell sourceCell : cellList) {
            targetCell = targetRow.addNewTableCell();
            // 列属性
            targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
            // 段落属性
            List<XWPFParagraph> paras = sourceCell.getParagraphs();
            while (targetCell.getParagraphs().size() < paras.size()) {
                targetCell.addParagraph();
            }
            for (int i = 0; i < paras.size(); i++) {
                XWPFParagraph para = paras.get(i);
                XWPFParagraph targetPara = targetCell.getParagraphs().get(i);
                targetPara.getCTP().setPPr(para.getCTP().getPPr());
                List<XWPFRun> runs = para.getRuns();
                for (XWPFRun xwpfRun : runs) {
                    XWPFRun targetRun = targetPara.createRun();
                    XWPFUtils.copyRun(xwpfRun, () -> targetRun);
                }
            }
        }
    }

    /**
     * 清除动态所有
     *
     * @param type 类型
     */
    private void clearDynamicAll(TableExpressionType type) {
        for (TableDynamicCellExcpression cellExcpression : type.getDynamicCells()) {
            this.setCellText(cellExcpression.getCell(), "");
        }
    }

    /**
     * 设置单元格的值
     *
     * @param cell
     * @param text
     */
    private void setCellText(XWPFTableCell cell, String text) {
        List<XWPFParagraph> paras = cell.getParagraphs();
        boolean isSet = true;
        for (XWPFParagraph para : paras) {
            List<XWPFRun> runs = para.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                if (isSet) {
                    this.insertCopyRun(para, runs, i, text);
                    isSet = false;
                } else {
                    this.insertCopyRun(para, runs, i, "");
                }
            }
        }
    }

    /**
     * 获取表的表达式类型
     *
     * @param session 会话
     * @param table   表
     * @return
     */
    private TableExpressionType getTableExpressionType(WordSession session, XWPFTable table) {
        List<XWPFTableRow> rows = table.getRows();
        int generalCount = 0;
        int dynamicRows = 0;
        String dynamicMemberName = null;
        XWPFTableRow functionFow = null;
        int beginRow = -1;
        List<XWPFTableCell> generalCells = new ArrayList<>();
        List<TableDynamicCellExcpression> dynamicCells = new ArrayList<>();
        for (int r = 0; r < rows.size(); r++) {
            int generalCellCount = 0;
            int dynamicCellCount = 0;
            XWPFTableRow row = rows.get(r);
            int colIndex = 0;
            for (XWPFTableCell cell : row.getTableCells()) {
                String cellText = cell.getText();
                if (!StringUtils.isNullOrBlank(cellText)) {
                    Matcher matcher = this.matcher(cellText, session.getToken().getGeneralBeginMark(), session.getToken().getGeneralEndMark());
                    if (matcher.find()) {
                        generalCount++;
                        generalCellCount++;
                        generalCells.add(cell);
                    }
                    matcher = this.matcher(cellText, session.getToken().getCollectionBeginMark(), session.getToken().getCollectionEndMark());
                    if (matcher.find()) {
                        if (matcher.groupCount() > 1) {
                            throw new WordEvaluateException("表格的集合表达式[" + cellText + "]的每个单元格只能允许一个表达式。");
                        }
                        String[] funItems = StringUtils.split("\\" + session.getToken().getArrayConnectMark(),
                                matcher.group(1), true);
                        if (funItems.length < 2) {
                            throw new WordEvaluateException("表格的集合表达式[" + cellText + "]不正确，应当为 成员名称.子成员名称 。");
                        }
                        String name = funItems[0].trim();
                        if (!StringUtils.isNullOrBlank(dynamicMemberName)
                                && !name.equalsIgnoreCase(dynamicMemberName)) {
                            throw new WordEvaluateException("集合表达式[" + cellText + "]所在的表格，有多个集合[" + name + ","
                                    + dynamicMemberName + "]，一个表格只支持一个集合 。");
                        }
                        boolean isMergeRow = false;
                        int expBeginIndex = 1;
                        if (funItems.length > 2 && funItems[1].equals(session.getToken().getArrayMergeRowMark())) {
                            isMergeRow = true;
                            expBeginIndex = 2;
                        }
                        StringBuilder expression = new StringBuilder();
                        for (int i = expBeginIndex; i < funItems.length; i++) {
                            expression.append(funItems[i]);
                        }
                        dynamicMemberName = name;
                        dynamicCellCount++;
                        if (functionFow != null) {
                            if (beginRow != r) {
                                throw new WordEvaluateException("同一表格的集合表达不能超过两行表达式。");
                            }
                        } else {
                            functionFow = row;
                            beginRow = r;
                            dynamicRows++;
                        }
                        TableDynamicCellExcpression cellExcpression = new TableDynamicCellExcpression(cell, colIndex,
                                expression.toString(), isMergeRow);
                        dynamicCells.add(cellExcpression);
                    }
                }
                colIndex++;
            }
            if (generalCellCount > 0 && dynamicCellCount > 0) {
                throw new WordEvaluateException("同一表格的同一行不能同时包含常规表达式和集合表达。");
            }
        }
        TableExpressionType result;
        if (generalCount > 0 && dynamicRows > 0) {
            result = TableExpressionType.createGeneralAndDynamic(functionFow, beginRow);
        } else if (dynamicRows > 0) {
            result = TableExpressionType.createDynamic(functionFow, beginRow);
        } else if (generalCount > 0) {
            result = TableExpressionType.createGeneral();
        } else {
            result = TableExpressionType.createNone();
        }
        result.setGeneralCells(generalCells);
        result.setDynamicCells(dynamicCells);
        result.setDynamicMemberName(dynamicMemberName);
        return result;
    }

    /**
     * 解析值
     *
     * @param session    会话
     * @param expression
     * @param beginMark
     * @param endMark
     * @return
     */
    private Variant evaluateValue(WordSession session, String expression, String beginMark, String endMark) {
        if (StringUtils.isNullOrBlank(expression)) {
            return Variant.DEFAULT;
        }
        try {
            return session.getEvaluator().parse(this.getExpression(expression.trim()), session.getContext());
        } catch (Exception e) {
            throw ExceptionUtils
                    .throwValidationException("\"" + beginMark + expression + endMark + "\" 语法出错:" + e.getMessage());
        }
    }

    /**
     * 处理语法
     *
     * @param expression
     * @return
     */
    private String getExpression(String expression) {
        String value = expression;
        while (value.contains(" (")) // 处理掉语法上的解析异常
        {
            value = value.replace(" (", "(");
        }
        return value;
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @param beginMark 开始标记
     * @param endMark   结束标记
     * @return
     */
    public Matcher matcher(String str, String beginMark, String endMark) {
        StringBuilder regex = new StringBuilder();
        this.addConvertToken(regex, beginMark);
        regex.append("([\\s\\S]*?)");
        this.addConvertToken(regex, endMark);
        Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str);
    }

    /**
     * @param regex
     * @param value
     */
    private void addConvertToken(StringBuilder regex, String value) {
        for (int i = 0; i < value.length(); i++) {
            regex.append("\\" + value.charAt(i));
        }
    }
}
