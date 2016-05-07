/*
Copyright (C) MARCH-2014 Pivotal Software, Inc.

All rights reserved. This program and the accompanying materials
are made available under the terms of the under the Apache License,
Version 2.0 (the "License”); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package pivotal.au.se.gemfirexdweb.dao.indexes;

public class Index 
{
	private String index;
	private String unique;
	private String table;
	private String indexType;
	private String columnsAndOrder;
	private String schemaName;
	private String caseSensitive;
	
	
	public Index(String index, String unique, String table, String indexType,
			String columnsAndOrder, String schemaName, String caseSensitive) {
		super();
		this.index = index;
		this.unique = unique;
		this.table = table;
		this.indexType = indexType;
		this.columnsAndOrder = columnsAndOrder;
		this.schemaName = schemaName;
		this.caseSensitive = caseSensitive;
	}
	
	public String getIndex() {
		return index;
	}
	
	public void setIndex(String index) {
		this.index = index;
	}
	
	public String getUnique() {
		return unique;
	}
	
	public void setUnique(String unique) {
		this.unique = unique;
	}
	
	public String getTable() {
		return table;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public String getIndexType() {
		return indexType;
	}
	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}
	
	public String getColumnsAndOrder() {
		return columnsAndOrder;
	}
	
	public void setColumnsAndOrder(String columnsAndOrder) {
		this.columnsAndOrder = columnsAndOrder;
	}

	
	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	
	public String getCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(String caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	@Override
	public String toString() {
		return "Index [index=" + index + ", unique=" + unique + ", table="
				+ table + ", indexType=" + indexType + ", columnsAndOrder="
				+ columnsAndOrder + ", schemaName=" + schemaName
				+ ", caseSensitive=" + caseSensitive + "]";
	}
	
	
	
}
