/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */

#include <iosfwd>

#include <thrift/Thrift.h>
#include <thrift/TApplicationException.h>
#include <thrift/protocol/TProtocol.h>
#include <thrift/transport/TTransport.h>

#include <thrift/cxxfunctional.h>
#include "snappydata_struct_SnappyExceptionData.h"

#include <algorithm>
#include <ostream>

#include <thrift/TToString.h>

namespace io { namespace snappydata { namespace thrift {


SnappyExceptionData::~SnappyExceptionData() throw() {
}


void SnappyExceptionData::__set_reason(const std::string& val) {
  this->reason = val;
}

void SnappyExceptionData::__set_sqlState(const std::string& val) {
  this->sqlState = val;
}

void SnappyExceptionData::__set_severity(const int32_t val) {
  this->severity = val;
}

uint32_t SnappyExceptionData::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_reason = false;
  bool isset_sqlState = false;
  bool isset_severity = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->reason);
          isset_reason = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->sqlState);
          isset_sqlState = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 3:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          xfer += iprot->readI32(this->severity);
          isset_severity = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_reason)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_sqlState)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_severity)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t SnappyExceptionData::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("SnappyExceptionData");

  xfer += oprot->writeFieldBegin("reason", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->reason);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("sqlState", ::apache::thrift::protocol::T_STRING, 2);
  xfer += oprot->writeString(this->sqlState);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("severity", ::apache::thrift::protocol::T_I32, 3);
  xfer += oprot->writeI32(this->severity);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(SnappyExceptionData &a, SnappyExceptionData &b) {
  using ::std::swap;
  swap(a.reason, b.reason);
  swap(a.sqlState, b.sqlState);
  swap(a.severity, b.severity);
}

SnappyExceptionData::SnappyExceptionData(const SnappyExceptionData& other169) {
  reason = other169.reason;
  sqlState = other169.sqlState;
  severity = other169.severity;
}
SnappyExceptionData::SnappyExceptionData( SnappyExceptionData&& other170) {
  reason = std::move(other170.reason);
  sqlState = std::move(other170.sqlState);
  severity = std::move(other170.severity);
}
SnappyExceptionData& SnappyExceptionData::operator=(const SnappyExceptionData& other171) {
  reason = other171.reason;
  sqlState = other171.sqlState;
  severity = other171.severity;
  return *this;
}
SnappyExceptionData& SnappyExceptionData::operator=(SnappyExceptionData&& other172) {
  reason = std::move(other172.reason);
  sqlState = std::move(other172.sqlState);
  severity = std::move(other172.severity);
  return *this;
}
void SnappyExceptionData::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "SnappyExceptionData(";
  out << "reason=" << to_string(reason);
  out << ", " << "sqlState=" << to_string(sqlState);
  out << ", " << "severity=" << to_string(severity);
  out << ")";
}

}}} // namespace