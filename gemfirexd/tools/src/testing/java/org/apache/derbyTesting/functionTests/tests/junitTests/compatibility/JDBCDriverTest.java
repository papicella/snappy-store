/**

   Derby - Class org.apache.derbyTesting.functionTests.tests.compatibility.JDBCDriverTest

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to you under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

/*
 * Changes for GemFireXD distributed data platform (some marked by "GemStone changes")
 *
 * Portions Copyright (c) 2010-2015 Pivotal Software, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License. See accompanying
 * LICENSE file.
 */
/**
 * <p>
 * This JUnit test verifies the compatibility of Derby clients and
 * servers across Derby version levels and supported VMs.
 * </p>
 *
 */

package org.apache.derbyTesting.functionTests.tests.junitTests.compatibility;

import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;

import junit.framework.*;

import org.apache.derbyTesting.functionTests.util.DerbyJUnitTest;

public	class	JDBCDriverTest	extends	CompatibilitySuite
{
	/////////////////////////////////////////////////////////////
	//
	//	CONSTANTS
	//
	/////////////////////////////////////////////////////////////

	private	static	final			String	ALL_TYPES_TABLE = "allTypesTable";
	private	static	final			String	KEY_COLUMN = "keyCol";

	//
	// Data values to be stuffed into columns of ALL_TYPES_TABLE.
	//
	private	static	final			byte[]	SAMPLE_BYTES =
		new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5 };
	private	static	final			String	SAMPLE_STRING = "hello";

	//
	// These funny constants are defined this way to make the salient
	// facts of the COERCIONS table leap out at you.
	//
	private	static	final			boolean	Y = true;
	private	static	final			boolean	_ = false;

	//
	// This table declares the datatypes supported by Derby and the earliest
	// versions of the Derby and the db2jcc client which support these
	// datatypes.
	//
	// If you add a type to this table, make sure you add a corresponding
	// column to the following row table. Also add a corresponding row to the
	// COERCIONS table.
	//
	private	static	final	TypeDescriptor[]	ALL_TYPES =
	{
		// 10.0 types
		
		new TypeDescriptor
		( Types.BIGINT,			"bigint",						IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.BLOB,			"blob",							IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.CHAR,			"char(5)",						IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.BINARY,			"char(5) for bit data",			IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.CLOB,			"clob",							IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.DATE,			"date",							IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.DECIMAL,		"decimal",						IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.DOUBLE,			"double",						IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.DOUBLE,			"double precision",				IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.REAL,			"float(23)",					IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.DOUBLE,			"float",						IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.INTEGER,		"integer",						IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.LONGVARCHAR,	"long varchar",					IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.LONGVARBINARY,	"long varchar for bit data",	IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.NUMERIC,		"numeric",						IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.REAL,			"real",							IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.SMALLINT,		"smallint",						IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.TIME,			"time",							IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.TIMESTAMP,		"timestamp",					IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.VARCHAR,		"varchar(5)",					IBM_2_4,	DRB_10_0,	VM_1_3 ),
		new TypeDescriptor
		( Types.VARBINARY,		"varchar(5) for bit data",		IBM_2_4,	DRB_10_0,	VM_1_3 ),
	};

	//
	// This table needs to have the same number of entries as ALL_TYPES.
	// The testSanity() test case enforces this at run time.
	//
	private	static	final	Object[]	ROW_1 =
	{
		// 10.0 columns
		
		new Long( 1L ),
		new MyBlob( SAMPLE_BYTES ),
		SAMPLE_STRING,
		SAMPLE_BYTES,
		new MyClob( SAMPLE_STRING ),
		new java.sql.Date( 1L ),
		new BigDecimal( 1.0 ),
		new Double( 1.0 ),
		new Double( 1.0 ),
		new Float( (float) 1.0 ),
		new Double( 1.0 ),
		new Integer( 1 ),
		SAMPLE_STRING,
		SAMPLE_BYTES,
		new BigDecimal( 1.0 ),
		new Float( (float) 1.0 ),
		new Short( (short) 1 ),
		new Time( 1L ),
		new Timestamp( 1L ),
		SAMPLE_STRING,
		SAMPLE_BYTES,
	};

	//
	// This table needs to have the same number of rows as ALL_TYPES.
	// Each row in this table needs to have the same number of columns as
	// rows in ALL_TYPES. The testSanity() test case enforces this at run time.
	// Note how the funny synonyms for true and false
	// make the salient facts of this table leap out at you.
	//
	// The ugly class name T_CN is an abbreviation which makes it possible to
	// squeeze this table onto a readable screen.
	//
	// Please read the introductory comment top-to-bottom. 'Y' means a coercion
	// is legal; '_' means it isn't.
	//
	private	static	final	T_CN[]	COERCIONS =
	{
		//												  B|B|C|B|C|D|D|D|R|I|L|L|N|R|S|T|T|V|V
		//												  I|L|H|I|L|A|E|O|E|N|O|O|U|E|M|I|I|A|A
		//												  G|O|A|N|O|T|C|U|A|T|N|N|M|A|A|M|M|R|R
		//												  I|B|R|A|B|E|I|B|L|E|G|G|E|L|L|E|E|C|B
		//												  N|-|-|R|-|-|M|L|-|G|V|V|R|-|L|-|S|H|I
		//												  T|-|-|Y|-|-|A|E|-|E|A|A|I|-|I|-|T|A|N
		//												  -|-|-|-|-|-|L|-|-|R|R|R|C|-|N|-|A|R|A
		//												  -|-|-|-|-|-|-|-|-|-|C|B|-|-|T|-|M|-|R
		//												  -|-|-|-|-|-|-|-|-|-|H|I|-|-|-|-|P|-|Y
		//												  -|-|-|-|-|-|-|-|-|-|A|N|-|-|-|-|-|-|-
		//												  -|-|-|-|-|-|-|-|-|-|R|A|-|-|-|-|-|-|-
		//												  -|-|-|-|-|-|-|-|-|-|-|R|-|-|-|-|-|-|-
		//												  -|-|-|-|-|-|-|-|-|-|-|Y|-|-|-|-|-|-|-
		new T_CN( Types.BIGINT, new boolean[]			{ Y,_,Y,_,_,_,_,Y,Y,Y,Y,_,Y,Y,Y,_,_,Y,_ } ),
		new T_CN( Types.BLOB, new boolean[] 			{ _,Y,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_ } ),
		new T_CN( Types.CHAR, new boolean[]				{ _,_,Y,_,_,_,_,_,_,_,Y,_,_,_,_,_,_,Y,_ } ),
		new T_CN( Types.BINARY, new boolean[]			{ _,_,_,Y,_,_,_,_,_,_,_,Y,_,_,_,_,_,_,Y } ),
		new T_CN( Types.CLOB, new boolean[]				{ _,_,_,_,Y,_,_,_,_,_,_,_,_,_,_,_,_,_,_ } ),
		new T_CN( Types.DATE, new boolean[]				{ _,_,_,_,_,Y,_,_,_,_,_,_,_,_,_,_,_,_,_ } ),
		new T_CN( Types.DECIMAL, new boolean[]			{ Y,_,_,_,_,_,Y,Y,Y,Y,Y,_,Y,Y,Y,_,_,Y,_ } ),
		new T_CN( Types.DOUBLE, new boolean[]			{ Y,_,_,_,_,_,Y,Y,Y,Y,Y,_,Y,Y,Y,_,_,Y,_ } ),
		new T_CN( Types.REAL, new boolean[]				{ Y,_,Y,_,_,_,Y,Y,Y,Y,Y,_,Y,Y,Y,_,_,Y,_ } ),
		new T_CN( Types.INTEGER, new boolean[]			{ Y,_,Y,_,_,_,Y,Y,Y,Y,Y,_,Y,Y,Y,_,_,Y,_ } ),
		new T_CN( Types.LONGVARCHAR, new boolean[]		{ _,_,Y,_,_,_,_,_,_,_,Y,_,_,_,_,_,_,Y,_ } ),
		new T_CN( Types.LONGVARBINARY, new boolean[]	{ _,_,_,_,_,_,_,_,_,_,_,Y,_,_,_,_,_,_,Y } ),
		new T_CN( Types.NUMERIC, new boolean[]			{ Y,_,Y,_,_,_,Y,Y,Y,Y,Y,_,Y,Y,Y,_,_,Y,_ } ),
		new T_CN( Types.REAL, new boolean[]				{ Y,_,Y,_,_,_,Y,Y,Y,Y,Y,_,Y,Y,Y,_,_,Y,_ } ),
		new T_CN( Types.SMALLINT, new boolean[]			{ Y,_,Y,_,_,_,Y,Y,Y,Y,Y,_,Y,Y,Y,_,_,Y,_ } ),
		new T_CN( Types.TIME, new boolean[]				{ _,_,_,_,_,_,_,_,_,_,_,_,_,_,_,Y,_,_,_ } ),
		new T_CN( Types.TIMESTAMP, new boolean[]		{ _,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,Y,_,_ } ),
		new T_CN( Types.VARCHAR, new boolean[]			{ _,_,Y,_,_,_,_,_,_,_,Y,_,_,_,_,_,_,Y,_ } ),
		new T_CN( Types.VARBINARY, new boolean[]		{ _,_,_,_,_,_,_,_,_,_,_,Y,_,_,_,_,_,_,Y } ),
	};

	/////////////////////////////////////////////////////////////
	//
	//	STATE
	//
	/////////////////////////////////////////////////////////////

	// map derby type name to type descriptor
	private	static	HashMap		_types = new HashMap();	// maps Derby type names to TypeDescriptors

	// map jdbc type to index into COERCIONS
	private	static	HashMap		_coercionIndex = new HashMap();	// maps jdbc types to legal coercions

	/////////////////////////////////////////////////////////////
	//
	//	CONSTRUCTOR
	//
	/////////////////////////////////////////////////////////////
	
	public	JDBCDriverTest() {}

	/////////////////////////////////////////////////////////////
	//
	//	TEST ENTRY POINTS
	//
	/////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * Sanity check the integrity of this test suite.
	 * </p>
	 */
	public	void	testSanity()
	{
		assertEquals
			( "ALL_TYPES.length == ROW_1.length", ALL_TYPES.length, ROW_1.length );

		// make sure there we completely describe the coercibility of every jdbc type
		int		coercionCount = COERCIONS.length;
		for ( int i = 0; i < coercionCount; i++ )
		{ assertEquals( "Coercion " + i, coercionCount, COERCIONS[ i ].getCoercions().length ); }
	}

	/**
	 * <p>
	 * Main test of jdbc drivers.
	 * </p>
	 */
	public	void	testJDBCDriver()
		throws Exception
	{
		Connection		conn = getConnection();
		
		dropSchema( conn );
		createSchema( conn );

		datatypesTest( conn );
        udtTest( conn );

		close( conn );
	}
	
	/////////////////////////////////////////////////////////////
	//
	//	TEST UDTs
	//
	/////////////////////////////////////////////////////////////

	//
	// Verify the metadata for user defined types.
	//
	private	void	udtTest( Connection conn )
		throws Exception
	{
        //
        // We must expect the wrong metadata in a network configuration
        // unless both the client and the server are at 10.6 or higher.
        // See DERBY-4491.
        //
        boolean correctBehavior =
            usingEmbeddedClient() ||
            (
             getServerVersion().atLeast( DRB_10_6 ) &&
             getDriverVersion().atLeast( DRB_10_6 )
             );

        String query = "select aliasinfo from sys.sysaliases";
        
        if ( correctBehavior )
        {
            String aliasInfoClassName = "com.pivotal.gemfirexd.internal.catalog.AliasInfo";
            
            checkRSMD
                (
                 conn,
                 query,
                 aliasInfoClassName,
                 15,
                 java.sql.Types.JAVA_OBJECT,
                 aliasInfoClassName,
                 0,
                 0
                 );
        }
        else
        {
            checkRSMD
                (
                 conn,
                 query,
                 "byte[]",
                 65400,
                 java.sql.Types.LONGVARBINARY,
                 "LONG VARCHAR FOR BIT DATA",
                 32700,
                 0
                 );
        }


        if ( serverSupportsUDTs() )
        {
            query = "select a from t_price";
            PreparedStatement ps = conn.prepareStatement( query );
            ResultSet rs = ps.executeQuery();
            rs.next();
            Object price = rs.getObject( 1 );
            String actualClassName = price.getClass().getName();
            rs.close();
            ps.close();

            if ( correctBehavior )
            {
                String priceClassName = "org.apache.derbyTesting.functionTests.tests.lang.Price";
                checkRSMD
                    (
                     conn,
                     query,
                     priceClassName,
                     15,
                     java.sql.Types.JAVA_OBJECT,
                     "\"APP\".\"PRICE\"",
                     0,
                     0
                     );

                assertEquals( priceClassName, actualClassName );
            }
            else
            {
                checkRSMD
                    (
                     conn,
                     query,
                     "byte[]",
                     65400,
                     java.sql.Types.LONGVARBINARY,
                     "LONG VARCHAR FOR BIT DATA",
                     32700,
                     0
                     );
                
                assertEquals( "java.lang.String", actualClassName );
            }

            query = "insert into t_price( a ) values ( ? )";
            
            if ( correctBehavior )
            {
                checkPMD
                    (
                     conn,
                     query,
                     "org.apache.derbyTesting.functionTests.tests.lang.Price",
                     java.sql.Types.JAVA_OBJECT,
                     "\"APP\".\"PRICE\"",
                     0,
                     0
                     );
            }
            else
            {
                checkPMD
                    (
                     conn,
                     query,
                     "byte[]",
                     java.sql.Types.LONGVARBINARY,
                     "LONG VARCHAR FOR BIT DATA",
                     32700,
                     0
                     );
            }

            //
            // Should only be able to stuff an object into the column if both
            // the client and server are at 10.6 or higher.
            //
            ps = conn.prepareStatement( query );
            byte[] someBytes = new byte[] { (byte) 1, (byte) 2, (byte) 3 };
            ByteArrayInputStream bais = new ByteArrayInputStream( someBytes );
            
            try {
                ps.setObject( 1, org.apache.derbyTesting.functionTests.tests.lang.Price.makePrice() );
                ps.executeUpdate();

                if ( !correctBehavior ) { fail( "setObject( Price ) unexpectedly worked." ); }
            }
            catch (SQLException se)
            {
                if ( correctBehavior ) { fail( "setObject( Price ) unexpectedly failed." ); }
            }
            try {
                ps.setObject( 1, someBytes );
                ps.executeUpdate();

                fail( "setObject( byte[] ) unexpectedly worked." );
            }
            catch (SQLException se) {}
            try {
                ps.setBytes( 1, someBytes );
                ps.executeUpdate();

                fail( "setBytes( byte[] ) unexpectedly worked." );
            }
            catch (SQLException se) {}
            try {
                ps.setBinaryStream( 1, bais, 3 );
                ps.executeUpdate();

                fail( "setBinaryStream( InputStream ) unexpectedly worked." );
            }
            catch (SQLException se) {}

            ps.close();
        }
    }
    
    /**
     * Check the ResultSetMetaData for a query whose first column is a UDT.
     */
    private void checkRSMD
        (
         Connection conn,
         String query,
         String expectedClassName,
         int expectedDisplaySize,
         int expectedJDBCType,
         String expectedSQLTypeName,
         int expectedPrecision,
         int expectedScale
         ) throws Exception
    {
        PreparedStatement ps = conn.prepareStatement( query );
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();

        assertEquals( expectedClassName, rsmd.getColumnClassName( 1 ) );
        assertEquals( expectedDisplaySize , rsmd.getColumnDisplaySize( 1 ));
        assertEquals( expectedJDBCType, rsmd.getColumnType( 1 ) );
        assertEquals( expectedSQLTypeName, rsmd.getColumnTypeName( 1 ) );
        assertEquals( expectedPrecision, rsmd.getPrecision( 1 ) );
        assertEquals( expectedScale, rsmd.getScale( 1 ) );

        rs.close();
        ps.close();
    }
    
    /**
     * Check the ParameterMetaData for a statement whose first parameter is a UDT.
     */
    private void checkPMD
        (
         Connection conn,
         String query,
         String expectedClassName,
         int expectedJDBCType,
         String expectedSQLTypeName,
         int expectedPrecision,
         int expectedScale
         ) throws Exception
    {
        PreparedStatement ps = conn.prepareStatement( query );
        ParameterMetaData pmd = ps.getParameterMetaData();

        assertEquals( pmd.getParameterClassName( 1 ), expectedClassName );
        assertEquals( pmd.getParameterType( 1 ), expectedJDBCType );
        assertEquals( pmd.getParameterTypeName( 1 ), expectedSQLTypeName );
        assertEquals( pmd.getPrecision( 1 ), expectedPrecision );
        assertEquals( pmd.getScale( 1 ), expectedScale );

        ps.close();
    }

	/////////////////////////////////////////////////////////////
	//
	//	TEST DATATYPES
	//
	/////////////////////////////////////////////////////////////

	//
	// Test that we can declare, insert, and select all datatypes that
	// are legal on the server. Test the metadata for these datatypes.
	//
	private	void	datatypesTest( Connection conn )
		throws Exception
	{
		TypeDescriptor[]	types = ALL_TYPES;
		String				tableName = ALL_TYPES_TABLE;
		Object[][]			rows = new Object[][] { makeNullRow( types.length ), ROW_1 };
		
		checkDBMetadata( conn, tableName );
		stuffTable( conn, tableName, types, rows );
		readTable( conn, tableName, types, rows, null );
	}
	
	//
	// Verify that we get the correct DatabaseMetaData for a table.
	//
	private	void	checkDBMetadata( Connection conn, String tableName )
		throws Exception
	{
		String				normalizedSchema = DEFAULT_USER_NAME.toUpperCase();
		String				normalizedTable = tableName.toUpperCase();
		DatabaseMetaData	dbmd = conn.getMetaData();

		ResultSet			rs = dbmd.getColumns
			( null, normalizedSchema, normalizedTable, "%" );

		println( "Pawing through database metadata for " + normalizedSchema + '.' + normalizedTable );

		while( rs.next() )
		{
			String			columnName = rs.getString( "COLUMN_NAME" );
			int				actualJdbcType = rs.getInt( "DATA_TYPE" );
			TypeDescriptor	typeDesc = getType( columnName );

			if ( columnName.equals( KEY_COLUMN ) ) { continue; }

			StringBuilder	buffer = new StringBuilder();

			buffer.append( "[ " );
			buffer.append( rs.getString( "COLUMN_NAME" ) );
			buffer.append( ",\t" );
			buffer.append( "type( " + rs.getInt( "DATA_TYPE" ) + " ),\t" );
			buffer.append( rs.getString( "TYPE_NAME" ) );
			buffer.append( " ]" );

			println( buffer.toString() );
			
			assertEquals( columnName, ddmdTypeKludge( typeDesc.getJdbcType() ), actualJdbcType );
		}

		close( rs );
	}

	//
	// Verify that we get the correct DatabaseMetaData for a procedure
	//
	private	void	checkProcMetadata( Connection conn, String procName, TypeDescriptor[] signature )
		throws Exception
	{
		String				normalizedSchema = DEFAULT_USER_NAME.toUpperCase();
		String				normalizedProc = procName.toUpperCase();
		DatabaseMetaData	dbmd = conn.getMetaData();

		ResultSet			rs = dbmd.getProcedureColumns
			( null, normalizedSchema, normalizedProc, "%" );

		println( "Pawing through database metadata for " + normalizedSchema + '.' + normalizedProc );

		while( rs.next() )
		{
			String			columnName = rs.getString( "COLUMN_NAME" );
			int				actualJdbcType = rs.getInt( "DATA_TYPE" );
			TypeDescriptor	typeDesc = getType( signature, columnName );

			if ( columnName.equals( KEY_COLUMN ) ) { continue; }

			StringBuilder	buffer = new StringBuilder();

			buffer.append( "[ " );
			buffer.append( rs.getString( "COLUMN_NAME" ) );
			buffer.append( ",\t" );
			buffer.append( "type( " + rs.getInt( "DATA_TYPE" ) + " ),\t" );
			buffer.append( rs.getString( "TYPE_NAME" ) );
			buffer.append( " ]" );

			println( buffer.toString() );
			
			assertEquals( columnName, ddmdTypeKludge( typeDesc.getJdbcType() ), actualJdbcType );
		}

		close( rs );
	}

	//
	// Stuff a table with rows
	//
	private	void	stuffTable
		( Connection conn, String tableName, TypeDescriptor[] types, Object[][] rows )
		throws Exception
	{
		PreparedStatement	ps = makeInsert( conn, tableName, types );
		int					rowCount = rows.length;

		for ( int i = 0; i < rowCount; i++ )
		{
			setRow( ps, i + 1, types, rows[ i ] );
		}
		
		close( ps );
	}

	private	PreparedStatement	makeInsert
		( Connection conn, String tableName, TypeDescriptor[] types )
		throws Exception
	{
		StringBuilder	masterBuffer = new StringBuilder();
		StringBuilder	columnBuffer = new StringBuilder();
		StringBuilder	valuesBuffer = new StringBuilder();
		int				columnNumber = 0;
		int				valuesNumber = 0;
		int				typeCount = types.length;

		beginColumnList( columnBuffer );
		beginColumnList( valuesBuffer );

		addColumn( columnBuffer, columnNumber++, doubleQuote( KEY_COLUMN ) );
		addColumn( valuesBuffer, valuesNumber++, "?" );
		
		for ( int i = 0; i < typeCount; i++ )
		{
			TypeDescriptor	type = types[ i ];
			
			if ( getServerVersion().atLeast( type.getDerbyVersion() ) )
			{
				String	typeName = type.getDerbyTypeName();
				String	columnDesc = doubleQuote( typeName );
				
				addColumn( columnBuffer, columnNumber++, columnDesc );
				addColumn( valuesBuffer, valuesNumber++, "?" );
			}
		}

		endColumnList( columnBuffer );
		endColumnList( valuesBuffer );

		masterBuffer.append( "insert into " + tableName + "\n" );
		masterBuffer.append( columnBuffer.toString() );
		masterBuffer.append( "values\n" );
		masterBuffer.append( valuesBuffer.toString() );

		PreparedStatement	ps = prepare( conn, masterBuffer.toString() );

		return ps;
	}

	//
	// Verify that we can select all legal datatypes in a table.
	//
	private	void	readTable
		( Connection conn, String tableName, TypeDescriptor[] types, Object[][] rows, List casts )
		throws Exception
	{
		PreparedStatement	ps = readTableQuery( conn, tableName, types );
		ResultSet			rs = ps.executeQuery();

		checkRSMD( rs );
		checkRows( rs, types, rows, casts );
		
		close( rs );
		close( ps );
	}

	//
	// Make the select query
	//
	private	PreparedStatement	readTableQuery
		( Connection conn, String tableName, TypeDescriptor[] types )
		throws Exception
	{
		StringBuilder	buffer = new StringBuilder();
		int				columnNumber = 0;
		int				typeCount = types.length;

		buffer.append( "select \n" );

		addColumn( buffer, columnNumber++, doubleQuote( KEY_COLUMN ) );
		
		for ( int i = 0; i < typeCount; i++ )
		{
			TypeDescriptor	type = types[ i ];
			
			if ( getServerVersion().atLeast( type.getDerbyVersion() ) )
			{
				String	typeName = type.getDerbyTypeName();
				String	columnDesc = doubleQuote( typeName );
				
				addColumn( buffer, columnNumber++, columnDesc );
			}
		}

		buffer.append( "\nfrom " + tableName + "\n" );
		buffer.append( "order by " + doubleQuote( KEY_COLUMN ) );

		PreparedStatement	ps = prepare( conn, buffer.toString() );

		return ps;
	}
	
	//
	// Verify that we get the correct ResultSetMetaData for all datatypes
	// which are legal on the server.
	//
	private	void	checkRSMD( ResultSet rs )
		throws Exception
	{
		ResultSetMetaData	rsmd = rs.getMetaData();
		int					columnCount = rsmd.getColumnCount();
		int					firstTastyColumn = 0;

		println( "ResultSetMetaData:\n" );
		
		firstTastyColumn++;				// skip uninteresting key column

		for ( int i = firstTastyColumn; i < columnCount; i++ )
		{
			StringBuilder		buffer = new StringBuilder();
			int				columnID = i + 1;
			String			columnName = rsmd.getColumnName( columnID );
			TypeDescriptor	typeDesc = getType( columnName );
			int				expectedType = rsmdTypeKludge( typeDesc.getJdbcType() );
			int				actualType = rsmd.getColumnType( columnID );

			buffer.append( "[ " );
			buffer.append( columnName );
			buffer.append( ", type( " );
			buffer.append( actualType );
			buffer.append( " ), " );
			buffer.append( rsmd.getColumnTypeName( columnID ) );
			buffer.append( " ]\n" );
			
			println( buffer.toString() );

			assertEquals( columnName, expectedType, actualType );
		}

	}

	//
	// Verify that we select the values we
	// originally inserted into a table.
	//
	private	void	checkRows( ResultSet rs, TypeDescriptor[] types, Object[][] rows, List casts )
		throws Exception
	{
		int					rowCount = rows.length;

		for ( int i = 0; i < rowCount; i++ )
		{
			rs.next();
			checkRow( rs, types, rows[ i ], casts );
		}
	}

	//
	// Verify that we select the values we
	// originally inserted into a row.
	//
	private	void	checkRow( ResultSet rs, TypeDescriptor[] types, Object[] row, List casts )
		throws Exception
	{
		int				typeCount = types.length;

		for ( int i = 0; i < typeCount; i++ )
		{
			TypeDescriptor	type = types[ i ];
			
			if ( getServerVersion().atLeast( type.getDerbyVersion() ) )
			{
				String	columnName = type.getDerbyTypeName();
				Object	expectedValue = row[ i ];
				Object	actualValue = getColumn( rs, columnName, type );

				println( "Comparing column " + columnName + ": " + expectedValue + " to " + actualValue );
				compareObjects( columnName, expectedValue, actualValue );

				checkCoercions( rs, columnName, type, casts );
			}
		}
	}

	//
	// Verify all legal jdbc coercions of a data value.
	//
	private	void	checkCoercions( ResultSet rs, String columnName, TypeDescriptor type, List casts )
		throws Exception
	{
		T_CN		coercionDesc = COERCIONS[ getCoercionIndex( type.getJdbcType() ) ];
		boolean[]	coercions = coercionDesc.getCoercions();
		int			count = coercions.length;
		int			legalCoercions = 0;

		println( "Checking coercions for " + columnName );
		
		for ( int i = 0; i < count; i++ )
		{
			if ( coercions[ i ] )
			{
				legalCoercions++;

				int		jdbcType = COERCIONS[ i ].getJdbcType();
				Object	retval = getColumn( rs, columnName, jdbcType );

				if ( casts != null ) { casts.add( retval ); }

				println( "\t" + jdbcType + ":\t" + retval );
			}

		}
		// finally, try getObject()

		Object	objval = rs.getObject( columnName );
		if ( objval == null ) { println( "\tgetObject() = null" ); }
		else
		{
			StringBuilder	buffer = new StringBuilder();
			buffer.append( "\tgetObject() = " );
			buffer.append( objval.getClass().getName() );
			buffer.append( "( " );
			buffer.append( objval );
			buffer.append( " )" );
			println( buffer.toString() );
		}
	}
	
	//
	// This kludge compensates for the fact that the DRDA clients report
	// that NUMERIC columns are DECIMAL. See bug 584.
	//
	// In addition, booleans are handled oddly by down-rev clients.
	//
	private	int	rsmdTypeKludge( int originalJDbcType )
	{
		// The embedded client does the right thing.
		if ( usingEmbeddedClient() && getServerVMVersion().atLeast( VM_1_4 ) ) { return originalJDbcType; }
		
		switch( originalJDbcType )
		{
			//This kludge compensates for the fact that the DRDA clients report
			// that NUMERIC columns are DECIMAL. See bug 584.
			case Types.NUMERIC:
				if ( usingEmbeddedClient() )	{ return originalJDbcType; }
				else { return Types.DECIMAL; }

		    default:			return originalJDbcType;
		}
	}

	//
	// This kludge compensates for the fact that servers return
	// different jdbc types depending on their vm.
	//
	private	int	ddmdTypeKludge( int originalJDbcType )
	{
		switch( originalJDbcType )
		{
			case JDBC_BOOLEAN:
				if ( getServerVMVersion().atLeast( VM_1_4 ) ) { return originalJDbcType; }
				else { return Types.BIT; }

		    default:			return originalJDbcType;
		}
	}

	//
	// Insert a row into the ALL_TYPES table. The row contains all datatypes
	// that are legal on the server.
	//
	private	void	setRow( PreparedStatement ps, int keyValue, TypeDescriptor[] types, Object[] row )
		throws Exception
	{
		int				param = 1;
		int				typeCount = types.length;

		ps.setInt( param++, keyValue );

		for ( int i = 0; i < typeCount; i++ )
		{
			TypeDescriptor	type = types[ i ];
			Object			value = row[ i ];
			
			if ( getServerVersion().atLeast( type.getDerbyVersion() ) )
			{
				setParameter( ps, param++, type, value );
			}
		}

		ps.execute();
	}

	//
	// Add a row of null columns.
	//
	private	Object[][]	makeRows( Object[][] rows )
	{
		int			count = rows.length;
		int			columns = rows[0].length;
		Object[][]	result = new Object[ count + 1 ][];
		int			idx = 0;

		result[ idx++ ] = makeNullRow( columns );
		
		for ( int i = 0; i < count; i++ )
		{
			result[ idx++ ] = rows[ i ];
		}

		return result;
	}

	private	Object[]	makeNullRow( int rowLength )
	{
		return new Object[ rowLength ];
	}

	//
	// Index the TypeDescriptors by Derby type name.
	//
	private	void	buildTypeMap()
	{
		int				typeCount = ALL_TYPES.length;

		for ( int i = 0; i < typeCount; i++ ) { putType( ALL_TYPES[ i ] ); }
	}
	private	void	putType( TypeDescriptor type )
	{
		_types.put( type.getDerbyTypeName(), type );
	}

	//
	// Lookup TypeDescriptors by Derby type name.
	//
	private	TypeDescriptor	getType( String typeName )
	{
		if ( _types.size() == 0 ) { buildTypeMap(); }
		
		return (TypeDescriptor) _types.get( typeName );
	}

	//
	// Lookup TypeDescriptors by jdbc type
	//
	private	TypeDescriptor	getType( int jdbcType )
	{
		int			count = ALL_TYPES.length;

		for ( int i = 0; i < count; i++ )
		{
			TypeDescriptor	type = ALL_TYPES[ i ];

			if ( type.getJdbcType() == jdbcType ) { return type; }
		}

		return null;
	}

	//
	// Lookup TypeDescriptors by column name in an array of types
	//
	private	TypeDescriptor	getType( TypeDescriptor[] types, String typeName )
	{
		int		count = types.length;

		for ( int i = 0; i < count; i++ )
		{
			TypeDescriptor	type = types[ i ];

			if ( type.getDerbyTypeName().equals( typeName ) ) { return type; }
		}
		
		return null;
	}

	//
	// Index legal coercions by jdbc type.
	//
	private	void	buildCoercionMap()
	{
		int				count = COERCIONS.length;

		for ( int i = 0; i < count; i++ ) { putCoercionIndex( i ); }
	}
	private	void	putCoercionIndex( int index )
	{
		_coercionIndex.put( new Integer( COERCIONS[ index ].getJdbcType() ), new Integer( index ) );
	}

	//
	// Lookup the legal coercions for a given jdbc type.
	//
	private	int	getCoercionIndex( int jdbcType )
	{
		if ( _coercionIndex.size() == 0 ) { buildCoercionMap(); }
		
		return ((Integer) _coercionIndex.get( new Integer( jdbcType ) )).intValue();
	}
	
	/////////////////////////////////////////////////////////////
	//
	//	MINIONS
	//
	/////////////////////////////////////////////////////////////
	
	///////////////////
	//
	//	TYPE MANAGEMENT
	//
	///////////////////
	
	//////////////////
	//
	//	SCHEMA MINIONS
	//
	//////////////////

	//
	// Create all the tables needed by our test cases.
	//
	private	void	createSchema( Connection conn )
		throws Exception
	{
		createTable( conn, ALL_TYPES_TABLE, ALL_TYPES );

        createUDTObjects( conn );
	}

	//
	// Create a table modelling an array of datatypes.
	//
	private	void	createTable( Connection conn, String tableName, TypeDescriptor[] types )
		throws Exception
	{
		StringBuilder	buffer = new StringBuilder();
		int				columnNumber = 0;
		int				typeCount = types.length;

		buffer.append( "create table " + tableName + "\n" );
		beginColumnList( buffer );

		addColumn( buffer, columnNumber++, doubleQuote( KEY_COLUMN ) + "\tint" );
		
		for ( int i = 0; i < typeCount; i++ )
		{
			TypeDescriptor	type = types[ i ];
			
			if ( getServerVersion().atLeast( type.getDerbyVersion() ) )
			{
				String	typeName = type.getDerbyTypeName();
				String	columnDesc = doubleQuote( typeName ) + '\t' + typeName;
				
				addColumn( buffer, columnNumber++, columnDesc );
			}
		}

		endColumnList( buffer );

		PreparedStatement	ps = prepare( conn, buffer.toString() );

		ps.execute();

		close( ps );
	}

    //
    // Create an ANSI UDT and a table with that type of column--
    // if the server is at 10.6 or higher.
    //
    private void createUDTObjects( Connection conn ) throws Exception
    {
        if ( !serverSupportsUDTs() ) { return; }

        PreparedStatement ps;

        ps = conn.prepareStatement( "create type price external name 'org.apache.derbyTesting.functionTests.tests.lang.Price' language java\n" );
        ps.execute();
        ps.close();

        ps = conn.prepareStatement( "create function makePrice( ) returns price language java parameter style java no sql external name 'org.apache.derbyTesting.functionTests.tests.lang.Price.makePrice'\n" );
        ps.execute();
        ps.close();

        ps = conn.prepareStatement( "create table t_price( a price )\n" );
        ps.execute();
        ps.close();

        ps = conn.prepareStatement( "insert into t_price( a ) values ( makePrice() )\n" );
        ps.execute();
        ps.close();
    }


	//
	// Helper methods for declaring a table.
	//
	private	void	beginColumnList( StringBuilder buffer )
	{
		buffer.append( "(\n" );
	}
	private	void	endColumnList( StringBuilder buffer )
	{
		buffer.append( "\n)\n" );
	}
	private	void	addColumn( StringBuilder buffer, int columnNumber, String text  )
	{
		if ( columnNumber > 0 ) { buffer.append( "," ); }

		buffer.append( "\n\t" );
		buffer.append( text );
	}
	
	//
	// Drop the tables used by our test cases.
	//
	private	void	dropSchema( Connection conn )
	{
		dropTable( conn, ALL_TYPES_TABLE );
        dropUDTObjects( conn );
	}

    //
    // Drop objects needed by UDT tests. We only do this if the server
    // is at 10.6 or higher.
    //
    private void dropUDTObjects( Connection conn )
    {
        if ( !serverSupportsUDTs() ) { return; }

        dropFunction( conn, "MAKEPRICE" );
        dropTable( conn, "T_PRICE" );
        dropUDT( conn, "PRICE" );
    }

	//
	// Logic for stuffing a data value into a column, given its type.
	//
	private	void	setParameter( PreparedStatement ps, int param, TypeDescriptor type, Object value )
		throws Exception
	{
		int		jdbcType = type.getJdbcType();

		if ( value != null )
		{
			setParameter( ps, param, jdbcType, value );
			return;
		}
		else if ( clientSupports( type ) )
		{
			ps.setNull( param, jdbcType );

			return;
		}

		// client does not support nulls of this type.

		fail( "Unsupported Derby type: " + type.getDerbyTypeName() );
	}

	//
	// Logic for verifying that a value was stuffed correctly.
	//
	private	void	checkParameter( ResultSet rs, int param, Object value )
		throws Exception
	{
		Object	   	actualValue;

		if ( value == null )
		{
			return;
		}

		println( "Checking " + value.getClass().getName() );

		if ( value instanceof Boolean ) {  actualValue = new Boolean( rs.getBoolean( param ) ); }
		else if ( value instanceof Byte ) { actualValue = new Byte( rs.getByte( param ) ); }
		else if ( value instanceof Short ) { actualValue = new Short( rs.getShort( param ) ); }
		else if ( value instanceof Integer ) { actualValue = new Integer( rs.getInt( param ) ); }
		else if ( value instanceof Long ) { actualValue = new Long( rs.getLong( param ) ); }
		else if ( value instanceof Float ) { actualValue = new Float( rs.getFloat( param ) ); }
		else if ( value instanceof Double ) { actualValue = new Double( rs.getDouble( param ) ); }
		else if ( value instanceof String ) { actualValue = rs.getString( param ); }
		else if ( value instanceof BigDecimal ) { actualValue = rs.getBigDecimal( param ); }
		else
		{
			actualValue = rs.getObject( param );
		}

		assertTrue( value.equals( actualValue ) );
	}


	// return true if the client supports this datatype
	private	boolean	clientSupports( TypeDescriptor type )
	{
		Version		firstSupportedVersion;

		if ( usingDB2Client() ) { firstSupportedVersion = type.getDb2jccVersion(); }
		else { firstSupportedVersion = type.getDerbyVersion(); }

		if ( firstSupportedVersion == null ) { return false; }
		else { return getDriverVersion().atLeast( firstSupportedVersion ); }
	}
	
	//
	// Get a data value from a column, given its type.
	//
	private	Object	getColumn( ResultSet rs, String columnName, TypeDescriptor type )
		throws Exception
	{
		int			jdbcType = type.getJdbcType();

		return getColumn( rs, columnName, jdbcType );
	}
	//
	// Get a data value from a procedure's output arg, given its type.
	//
	private	Object	getOutArg( CallableStatement cs, int arg, TypeDescriptor type )
		throws Exception
	{
		int			jdbcType = type.getJdbcType();

		return getOutArg( cs, arg, jdbcType );
	}
	//
	// SQL code generation minions 
	//
	private	String	doubleQuote( String text )
	{
		return '"' + text + '"';
	}

	/////////////////////////////////////////////////////////////
	//
	//	INNER CLASSES
	//
	/////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * This helper class describes a legal datatype and the version of Derby
	 * and db2jcc where the datatype first appears.
	 * </p>
	 */
	public	static	final	class	TypeDescriptor
	{
		private	int		_jdbcType;
		private	String	_derbyTypeName;
		private	Version	_db2jccVersion;		// first db2jcc version which supports this type
		private	Version	_derbyVersion;		// first derby version which supports this type
		private	Version	_vmVersion;			// first vm (jdbc) version which supports this type

		public	TypeDescriptor
		(
		    int		jdbcType,
			String	derbyTypeName,
			Version	db2jccVersion,
			Version	derbyVersion,
			Version	vmVersion
		)
		{
			_jdbcType = jdbcType;
			_derbyTypeName = derbyTypeName;
			_db2jccVersion = db2jccVersion;
			_derbyVersion = derbyVersion;
			_vmVersion = vmVersion;
		}

		public	int		getJdbcType() 					{ return _jdbcType; }
		public	String	getDerbyTypeName()				{ return _derbyTypeName; }
		public	Version	getDb2jccVersion()				{ return _db2jccVersion; }
		public	Version	getDerbyVersion()				{ return _derbyVersion; }
		public	Version	getVMVersion()					{ return _vmVersion; }
	}

	/**
	 * <p>
	 * This helper class captures TypeCoercion logic. I have abbreviated it to
	 * this ugly class name so that the COERCIONS table will fit on a readable screen.
	 * </p>
	 */
	public	static	final	class	T_CN
	{
		private	int			_jdbcType;
		private	boolean[]	_coercions;

		public	T_CN( int jdbcType, boolean[] coercions )
		{
			_jdbcType = jdbcType;
			_coercions = coercions;
		}

		public	int			getJdbcType() 					{ return _jdbcType; }
		public	boolean[]	getCoercions() 					{ return _coercions; }
	}
	
	/**
	 * <p>
	 * A crude Blob implementation for datatype testing.
	 * </p>
	 */
	public	static	final	class	MyBlob	implements	Blob
	{
		private	byte[]	_bytes;

		public	MyBlob( byte[] bytes )
		{
			_bytes = bytes;
		}

		public	InputStream	getBinaryStream()
		{
			return new ByteArrayInputStream( _bytes );
		}

		public	byte[]	getBytes( long position, int length ) { return _bytes; }

		public	long	length() { return (long) _bytes.length; }

		public	long	position( Blob pattern, long start ) { return 0L; }
		public	long	position( byte[] pattern, long start ) { return 0L; }

		public	boolean	equals( Object other )
		{
			if ( other == null ) { return false; }
			if ( !( other instanceof Blob ) ) { return false; }

			Blob	that = (Blob) other;

			try {
				if ( this.length() != that.length() ) { return false; }

				InputStream	thisStream = this.getBinaryStream();
				InputStream	thatStream = that.getBinaryStream();

				while( true )
				{
					int		nextByte = thisStream.read();

					if ( nextByte < 0 ) { break; }
					if ( nextByte != thatStream.read() ) { return false; }
				}
			}
			catch (Exception e)
			{
				System.err.println( e.getMessage() );
				e.printStackTrace();
				return false;
			}

			return true;
		}

        public int setBytes(long arg0, byte[] arg1) throws SQLException {
            throw new SQLException("not implemented for this test");
        }

        public int setBytes(long arg0, byte[] arg1, int arg2, int arg3) throws SQLException {
            throw new SQLException("not implemented for this test");
        }

        public OutputStream setBinaryStream(long arg0) throws SQLException {
            throw new SQLException("not implemented for this test");
        }

        public void truncate(long arg0) throws SQLException {
            throw new SQLException("not implemented for this test");
        }
    
        // GemStone changes BEGIN
        // JDBC 4.0 method stubs so will compile with JDK 1.6
        public void free() throws SQLException {
          throw new SQLException("not implemented for this test");
        }
    
        public InputStream getBinaryStream(long pos, long length)
        throws SQLException {
          throw new SQLException("not implemented for this test");
        }
        // GemStone changes END
	}

	/**
	 * <p>
	 * A crude Clob implementation for datatype testing.
	 * </p>
	 */
	public	static	final	class	MyClob	implements	Clob
	{
		private	String	_contents;

		public	MyClob( String contents )
		{
			_contents = contents;
		}

		public	InputStream	getAsciiStream()
		{
			try {
				return new ByteArrayInputStream( _contents.getBytes( "UTF-8" ) );
			}
			catch (Exception e) { return null; }
		}

		public	Reader	getCharacterStream()
		{
			return new CharArrayReader( _contents.toCharArray() );
		}

		public	String	getSubString( long position, int length )
		{
			return _contents.substring( (int) position, length );
		}
		
		public	long	length() { return (long) _contents.length(); }

		public	long	position( Clob searchstr, long start ) { return 0L; }
		public	long	position( String searchstr, long start ) { return 0L; }

		public	boolean	equals( Object other )
		{
			if ( other == null ) { return false; }
			if ( !( other instanceof Clob ) ) { return false; }

			Clob	that = (Clob) other;

			try {
				if ( this.length() != that.length() ) { return false; }
			
				InputStream	thisStream = this.getAsciiStream();
				InputStream	thatStream = that.getAsciiStream();

				while( true )
				{
					int		nextByte = thisStream.read();

					if ( nextByte < 0 ) { break; }
					if ( nextByte != thatStream.read() ) { return false; }
				}
			}
			catch (Exception e)
			{
				System.err.println( e.getMessage() );
				e.printStackTrace();
				return false;
			}

			return true;
		}

        public int setString(long arg0, String arg1) throws SQLException {
            throw new SQLException("not implemented for this test");
        }

        public int setString(long arg0, String arg1, int arg2, int arg3) throws SQLException {
            throw new SQLException("not implemented for this test");
        }

        public OutputStream setAsciiStream(long arg0) throws SQLException {
            throw new SQLException("not implemented for this test");
        }

        public Writer setCharacterStream(long arg0) throws SQLException {
            throw new SQLException("not implemented for this test");
        }

        public void truncate(long arg0) throws SQLException {
            throw new SQLException("not implemented for this test");
            
        }
    
    // GemStone changes BEGIN
    // JDBC 4.0 method stubs so will compile with JDK 1.6
    public void free() throws SQLException {
      throw new SQLException("not implemented for this test");
    }
    
    public Reader getCharacterStream(long pos,
                              long length)
    throws SQLException {
      throw new SQLException("not implemented for this test");
    }
    // GemStone changes END
    

	}

}