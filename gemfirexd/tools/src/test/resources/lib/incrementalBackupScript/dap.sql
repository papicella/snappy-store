CREATE PROCEDURE SPROC_SELECT_DAP() 
LANGUAGE JAVA 
PARAMETER STYLE JAVA 
MODIFIES SQL DATA DYNAMIC RESULT SETS 1
EXTERNAL NAME 'dap.SprocSelectProcedure.execute';