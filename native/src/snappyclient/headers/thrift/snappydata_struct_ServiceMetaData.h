/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */

#ifndef SNAPPYDATA_STRUCT_SERVICEMETADATA_H
#define SNAPPYDATA_STRUCT_SERVICEMETADATA_H


#include "snappydata_struct_Decimal.h"
#include "snappydata_struct_Timestamp.h"
#include "snappydata_struct_JSONValue.h"
#include "snappydata_struct_JSONObject.h"
#include "snappydata_struct_BlobChunk.h"
#include "snappydata_struct_ClobChunk.h"

#include "snappydata_types.h"

namespace io { namespace snappydata { namespace thrift {

typedef struct _ServiceMetaData__isset {
  _ServiceMetaData__isset() : extraNameCharacters(false) {}
  bool extraNameCharacters :1;
} _ServiceMetaData__isset;

class ServiceMetaData {
 public:

  ServiceMetaData(const ServiceMetaData&);
  ServiceMetaData(ServiceMetaData&&);
  ServiceMetaData& operator=(const ServiceMetaData&);
  ServiceMetaData& operator=(ServiceMetaData&&);
  ServiceMetaData() : productName(), productVersion(), productMajorVersion(0), productMinorVersion(0), jdbcMajorVersion(0), jdbcMinorVersion(0), identifierQuote(), searchStringEscape(), extraNameCharacters(), schemaTerm(), procedureTerm(), catalogTerm(), catalogSeparator(), maxBinaryLiteralLength(0), maxCharLiteralLength(0), maxColumnsInGroupBy(0), maxColumnsInIndex(0), maxColumnsInOrderBy(0), maxColumnsInSelect(0), maxColumnsInTable(0), maxConnections(0), maxIndexLength(0), maxRowSize(0), maxStatementLength(0), maxOpenStatements(0), maxTableNamesInSelect(0), maxColumnNameLength(0), maxCursorNameLength(0), maxSchemaNameLength(0), maxProcedureNameLength(0), maxCatalogNameLength(0), maxTableNameLength(0), maxUserNameLength(0), defaultTransactionIsolation(0), defaultResultSetType(0), defaultResultSetHoldabilityHoldCursorsOverCommit(0), sqlStateIsXOpen(0), catalogAtStart(0), rowIdLifeTime((RowIdLifetime::type)0) {
  }

  virtual ~ServiceMetaData() throw();
  std::string productName;
  std::string productVersion;
  int32_t productMajorVersion;
  int32_t productMinorVersion;
  int32_t jdbcMajorVersion;
  int32_t jdbcMinorVersion;
  std::string identifierQuote;
  std::vector<std::string>  sqlKeywords;
  std::vector<std::string>  numericFunctions;
  std::vector<std::string>  stringFunctions;
  std::vector<std::string>  systemFunctions;
  std::vector<std::string>  dateTimeFunctions;
  std::string searchStringEscape;
  std::string extraNameCharacters;
  std::map<SnappyType::type, std::set<SnappyType::type> >  supportedCONVERT;
  std::string schemaTerm;
  std::string procedureTerm;
  std::string catalogTerm;
  std::string catalogSeparator;
  int32_t maxBinaryLiteralLength;
  int32_t maxCharLiteralLength;
  int32_t maxColumnsInGroupBy;
  int32_t maxColumnsInIndex;
  int32_t maxColumnsInOrderBy;
  int32_t maxColumnsInSelect;
  int32_t maxColumnsInTable;
  int32_t maxConnections;
  int32_t maxIndexLength;
  int32_t maxRowSize;
  int32_t maxStatementLength;
  int32_t maxOpenStatements;
  int32_t maxTableNamesInSelect;
  int32_t maxColumnNameLength;
  int32_t maxCursorNameLength;
  int32_t maxSchemaNameLength;
  int32_t maxProcedureNameLength;
  int32_t maxCatalogNameLength;
  int32_t maxTableNameLength;
  int32_t maxUserNameLength;
  int32_t defaultTransactionIsolation;
  int8_t defaultResultSetType;
  bool defaultResultSetHoldabilityHoldCursorsOverCommit;
  bool sqlStateIsXOpen;
  bool catalogAtStart;
  std::map<TransactionAttribute::type, bool>  transactionDefaults;
  RowIdLifetime::type rowIdLifeTime;
  std::set<ServiceFeature::type>  supportedFeatures;
  std::map<ServiceFeatureParameterized::type, std::vector<int32_t> >  featuresWithParams;

  _ServiceMetaData__isset __isset;

  void __set_productName(const std::string& val);

  void __set_productVersion(const std::string& val);

  void __set_productMajorVersion(const int32_t val);

  void __set_productMinorVersion(const int32_t val);

  void __set_jdbcMajorVersion(const int32_t val);

  void __set_jdbcMinorVersion(const int32_t val);

  void __set_identifierQuote(const std::string& val);

  void __set_sqlKeywords(const std::vector<std::string> & val);

  void __set_numericFunctions(const std::vector<std::string> & val);

  void __set_stringFunctions(const std::vector<std::string> & val);

  void __set_systemFunctions(const std::vector<std::string> & val);

  void __set_dateTimeFunctions(const std::vector<std::string> & val);

  void __set_searchStringEscape(const std::string& val);

  void __set_extraNameCharacters(const std::string& val);

  void __set_supportedCONVERT(const std::map<SnappyType::type, std::set<SnappyType::type> > & val);

  void __set_schemaTerm(const std::string& val);

  void __set_procedureTerm(const std::string& val);

  void __set_catalogTerm(const std::string& val);

  void __set_catalogSeparator(const std::string& val);

  void __set_maxBinaryLiteralLength(const int32_t val);

  void __set_maxCharLiteralLength(const int32_t val);

  void __set_maxColumnsInGroupBy(const int32_t val);

  void __set_maxColumnsInIndex(const int32_t val);

  void __set_maxColumnsInOrderBy(const int32_t val);

  void __set_maxColumnsInSelect(const int32_t val);

  void __set_maxColumnsInTable(const int32_t val);

  void __set_maxConnections(const int32_t val);

  void __set_maxIndexLength(const int32_t val);

  void __set_maxRowSize(const int32_t val);

  void __set_maxStatementLength(const int32_t val);

  void __set_maxOpenStatements(const int32_t val);

  void __set_maxTableNamesInSelect(const int32_t val);

  void __set_maxColumnNameLength(const int32_t val);

  void __set_maxCursorNameLength(const int32_t val);

  void __set_maxSchemaNameLength(const int32_t val);

  void __set_maxProcedureNameLength(const int32_t val);

  void __set_maxCatalogNameLength(const int32_t val);

  void __set_maxTableNameLength(const int32_t val);

  void __set_maxUserNameLength(const int32_t val);

  void __set_defaultTransactionIsolation(const int32_t val);

  void __set_defaultResultSetType(const int8_t val);

  void __set_defaultResultSetHoldabilityHoldCursorsOverCommit(const bool val);

  void __set_sqlStateIsXOpen(const bool val);

  void __set_catalogAtStart(const bool val);

  void __set_transactionDefaults(const std::map<TransactionAttribute::type, bool> & val);

  void __set_rowIdLifeTime(const RowIdLifetime::type val);

  void __set_supportedFeatures(const std::set<ServiceFeature::type> & val);

  void __set_featuresWithParams(const std::map<ServiceFeatureParameterized::type, std::vector<int32_t> > & val);

  bool operator == (const ServiceMetaData & rhs) const
  {
    if (!(productName == rhs.productName))
      return false;
    if (!(productVersion == rhs.productVersion))
      return false;
    if (!(productMajorVersion == rhs.productMajorVersion))
      return false;
    if (!(productMinorVersion == rhs.productMinorVersion))
      return false;
    if (!(jdbcMajorVersion == rhs.jdbcMajorVersion))
      return false;
    if (!(jdbcMinorVersion == rhs.jdbcMinorVersion))
      return false;
    if (!(identifierQuote == rhs.identifierQuote))
      return false;
    if (!(sqlKeywords == rhs.sqlKeywords))
      return false;
    if (!(numericFunctions == rhs.numericFunctions))
      return false;
    if (!(stringFunctions == rhs.stringFunctions))
      return false;
    if (!(systemFunctions == rhs.systemFunctions))
      return false;
    if (!(dateTimeFunctions == rhs.dateTimeFunctions))
      return false;
    if (!(searchStringEscape == rhs.searchStringEscape))
      return false;
    if (__isset.extraNameCharacters != rhs.__isset.extraNameCharacters)
      return false;
    else if (__isset.extraNameCharacters && !(extraNameCharacters == rhs.extraNameCharacters))
      return false;
    if (!(supportedCONVERT == rhs.supportedCONVERT))
      return false;
    if (!(schemaTerm == rhs.schemaTerm))
      return false;
    if (!(procedureTerm == rhs.procedureTerm))
      return false;
    if (!(catalogTerm == rhs.catalogTerm))
      return false;
    if (!(catalogSeparator == rhs.catalogSeparator))
      return false;
    if (!(maxBinaryLiteralLength == rhs.maxBinaryLiteralLength))
      return false;
    if (!(maxCharLiteralLength == rhs.maxCharLiteralLength))
      return false;
    if (!(maxColumnsInGroupBy == rhs.maxColumnsInGroupBy))
      return false;
    if (!(maxColumnsInIndex == rhs.maxColumnsInIndex))
      return false;
    if (!(maxColumnsInOrderBy == rhs.maxColumnsInOrderBy))
      return false;
    if (!(maxColumnsInSelect == rhs.maxColumnsInSelect))
      return false;
    if (!(maxColumnsInTable == rhs.maxColumnsInTable))
      return false;
    if (!(maxConnections == rhs.maxConnections))
      return false;
    if (!(maxIndexLength == rhs.maxIndexLength))
      return false;
    if (!(maxRowSize == rhs.maxRowSize))
      return false;
    if (!(maxStatementLength == rhs.maxStatementLength))
      return false;
    if (!(maxOpenStatements == rhs.maxOpenStatements))
      return false;
    if (!(maxTableNamesInSelect == rhs.maxTableNamesInSelect))
      return false;
    if (!(maxColumnNameLength == rhs.maxColumnNameLength))
      return false;
    if (!(maxCursorNameLength == rhs.maxCursorNameLength))
      return false;
    if (!(maxSchemaNameLength == rhs.maxSchemaNameLength))
      return false;
    if (!(maxProcedureNameLength == rhs.maxProcedureNameLength))
      return false;
    if (!(maxCatalogNameLength == rhs.maxCatalogNameLength))
      return false;
    if (!(maxTableNameLength == rhs.maxTableNameLength))
      return false;
    if (!(maxUserNameLength == rhs.maxUserNameLength))
      return false;
    if (!(defaultTransactionIsolation == rhs.defaultTransactionIsolation))
      return false;
    if (!(defaultResultSetType == rhs.defaultResultSetType))
      return false;
    if (!(defaultResultSetHoldabilityHoldCursorsOverCommit == rhs.defaultResultSetHoldabilityHoldCursorsOverCommit))
      return false;
    if (!(sqlStateIsXOpen == rhs.sqlStateIsXOpen))
      return false;
    if (!(catalogAtStart == rhs.catalogAtStart))
      return false;
    if (!(transactionDefaults == rhs.transactionDefaults))
      return false;
    if (!(rowIdLifeTime == rhs.rowIdLifeTime))
      return false;
    if (!(supportedFeatures == rhs.supportedFeatures))
      return false;
    if (!(featuresWithParams == rhs.featuresWithParams))
      return false;
    return true;
  }
  bool operator != (const ServiceMetaData &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const ServiceMetaData & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

  virtual void printTo(std::ostream& out) const;
};

void swap(ServiceMetaData &a, ServiceMetaData &b);

inline std::ostream& operator<<(std::ostream& out, const ServiceMetaData& obj)
{
  obj.printTo(out);
  return out;
}

}}} // namespace

#endif