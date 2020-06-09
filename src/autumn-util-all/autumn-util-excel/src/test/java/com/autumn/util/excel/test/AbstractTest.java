package com.autumn.util.excel.test;

import com.autumn.util.excel.sheet.WorkSheetHeader;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.column.AbstractColumn;
import com.autumn.util.excel.column.ColumnGroup;
import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.excel.enums.CellType;
import com.autumn.util.HorizontalAlignment;

import java.util.List;

/**
 * @author JuWa ▪ Zhang
 * @date 2017年12月18日
 */
public abstract class AbstractTest {
	
	public final static int TEST_FOR_COUNT = 6;
	/**
	 * 复杂的数据格式
	 * @return
	 */
	public WorkSheetInfo createWorkSheetInfo() {
		WorkSheetInfo work = new WorkSheetInfo();
		WorkSheetHeader header = work.getHeader();
		header.setShow(true);
		header.setName("用户信息");
		WorkSheetHeader childHeader = work.getChildHeader();
		childHeader.setName("条件");
		childHeader.setShow(true);
		List<AbstractColumn> columns1 = work.getColumns();
		ColumnInfo info1 = new ColumnInfo();
		info1.setAlignment(HorizontalAlignment.LEFT);
		info1.setCellType(CellType.CELL_TYPE_INTEGER);
		info1.setFriendlyName("User Id");
		info1.setOrder(1);
		info1.setPropertyName("userId");
//		info1.setMergeContentRow(false);
//		info1.setImportColumn(true);
		info1.setWidth(200);
		ColumnInfo info2 = new ColumnInfo();
		info2.setAlignment(HorizontalAlignment.LEFT);
		info2.setCellType(CellType.CELL_TYPE_STRING);
		info2.setFriendlyName("User Name");
		info2.setOrder(2);
		info2.setPropertyName("userName");
//		info2.setMergeContentRow(false);
		info2.setWidth(200);
		info2.setImportColumn(true);
		columns1.add(info1);
		columns1.add(info2);

		ColumnGroup group1 = new ColumnGroup();
		group1.setOrder(3);
		group1.setFriendlyName("A1");
		List<AbstractColumn> columns2 = group1.getColumns();
		ColumnInfo info3 = new ColumnInfo();
		info3.setAlignment(HorizontalAlignment.LEFT);
		info3.setCellType(CellType.CELL_TYPE_STRING);
		info3.setFriendlyName("A-1");
		info3.setOrder(1);
		info3.setPropertyName("a1");
		info3.setWidth(80);
		info3.setImportColumn(true);
		info3.setImportNotNullable(true);
		columns2.add(info3);

		ColumnInfo info4 = new ColumnInfo();
		info4.setAlignment(HorizontalAlignment.LEFT);
		info4.setCellType(CellType.CELL_TYPE_STRING);
		info4.setFriendlyName("A-2");
		info4.setOrder(2);
		info4.setPropertyName("a2");
		info4.setImportColumn(true);
		info4.setWidth(80);
		columns2.add(info4);
		columns1.add(group1);

		ColumnGroup group2 = new ColumnGroup();
		group2.setFriendlyName("A2");
		group2.setOrder(4);
		List<AbstractColumn> columns3 = group2.getColumns();
		ColumnInfo info5 = new ColumnInfo();
		info5.setAlignment(HorizontalAlignment.LEFT);
		info5.setCellType(CellType.CELL_TYPE_STRING);
		info5.setFriendlyName("A-3");
		info5.setOrder(1);
//		info5.setImportColumn(true);
		info5.setPropertyName("a1");
		info5.setWidth(80);
		columns3.add(info5);

		ColumnInfo info6 = new ColumnInfo();
		info6.setAlignment(HorizontalAlignment.LEFT);
		info6.setCellType(CellType.CELL_TYPE_STRING);
		info6.setFriendlyName("A-4");
		info6.setOrder(2);
		info6.setPropertyName("a2");
		info6.setWidth(80);
		columns3.add(info6);

		ColumnGroup group3 = new ColumnGroup();
		group3.setFriendlyName("A2-1");
		group3.setOrder(4);
		List<AbstractColumn> columns4 = group3.getColumns();

		ColumnInfo info7 = new ColumnInfo();
		info7.setAlignment(HorizontalAlignment.LEFT);
		info7.setCellType(CellType.CELL_TYPE_STRING);
		info7.setFriendlyName("A-5");
		info7.setOrder(1);
		info7.setPropertyName("a2");
		info7.setWidth(80);
		info7.setImportColumn(true);
		info7.setImportNotNullable(true);
		columns4.add(info7);

		ColumnInfo info8 = new ColumnInfo();
		info8.setAlignment(HorizontalAlignment.LEFT);
		info8.setCellType(CellType.CELL_TYPE_STRING);
		info8.setFriendlyName("A-6");
		info8.setOrder(2);
		info8.setPropertyName("a2");
		info8.setWidth(80);
		columns4.add(info8);
		columns3.add(group3);
		columns1.add(group2);

		return work;
	}

	/**
	 * 简单的数据格式
	 * @return
	 */
	public WorkSheetInfo createPlainWorkSheetInfo() {
		WorkSheetInfo info = new WorkSheetInfo();
		/*WorkbookHeader header = info.getHeader();
		header.setIsShow(true);
		header.setFontSize(20D);
		header.setName("用户信息表");
		header.setRowHeight(60);
		WorkbookHeader childHeader = info.getChildHeader();
		childHeader.setFontSize(15D);
		childHeader.setIsShow(true);
		childHeader.setName("2017-2018");
		childHeader.setRowHeight(40);*/
		List<AbstractColumn> columns = info.getColumns();
		ColumnInfo cInfo = new ColumnInfo();
		cInfo.setAlignment(HorizontalAlignment.RIGHT);
		cInfo.setCellType(CellType.CELL_TYPE_INTEGER);
		cInfo.setFriendlyName("USER ID");
		cInfo.setOrder(1);
		cInfo.setImportColumn(true);
		cInfo.setWidth(200);
		cInfo.setPropertyName("userId");
		columns.add(cInfo);
		
		ColumnInfo cInfo2 = new ColumnInfo();
		cInfo2.setAlignment(HorizontalAlignment.RIGHT);
		cInfo2.setCellType(CellType.CELL_TYPE_STRING);
		cInfo2.setFriendlyName("USER NAME");
		cInfo2.setOrder(2);
		cInfo2.setImportColumn(true);
//		cInfo2.setImportNotNullable(true);
		cInfo2.setWidth(200);
		cInfo2.setPropertyName("userName");
		cInfo2.setImportColumn(true);
		columns.add(cInfo2);
		
		ColumnInfo cInfo3 = new ColumnInfo();
		cInfo3.setAlignment(HorizontalAlignment.RIGHT);
		cInfo3.setCellType(CellType.CELL_TYPE_STRING);
		cInfo3.setFriendlyName("A1");
		cInfo3.setOrder(4);
		cInfo3.setImportColumn(true);
		cInfo3.setWidth(80);
		cInfo3.setPropertyName("a1");
		columns.add(cInfo3);
		
		ColumnInfo cInfo4 = new ColumnInfo();
		cInfo4.setAlignment(HorizontalAlignment.RIGHT);
		cInfo4.setCellType(CellType.CELL_TYPE_STRING);
		cInfo4.setFriendlyName("A2");
		cInfo4.setOrder(3);
		cInfo4.setImportColumn(true);
		cInfo4.setWidth(80);
		cInfo4.setPropertyName("a2");
		columns.add(cInfo4);
		return info;
	}
}

