/*
 * Copyright (c) 2010-2015 Pivotal Software, Inc. All rights reserved.
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
/*
 * Changes for SnappyData data platform.
 *
 * Portions Copyright (c) 2016 SnappyData, Inc. All rights reserved.
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
 * Parameters.cpp
 */

#include "Parameters.h"
#include "PreparedStatement.h"

using namespace io::snappydata;
using namespace io::snappydata::client;

Parameters::Parameters(const PreparedStatement& pstmt) :
    Row(pstmt.getParameterCount()) {
  m_values.resize(pstmt.getParameterCount());
}

Parameters& Parameters::setBoolean(const uint32_t index, const bool v) {
  if (index < m_values.size()) {
    m_values[index].setBool(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setByte(const uint32_t index, const int8_t v) {
  if (index < m_values.size()) {
    m_values[index].setByte(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setShort(const uint32_t index, const int16_t v) {
  if (index < m_values.size()) {
    m_values[index].setI16(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setInt(const uint32_t index, const int32_t v) {
  if (index < m_values.size()) {
    m_values[index].setI32(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setLong(const uint32_t index, const int64_t v) {
  if (index < m_values.size()) {
    m_values[index].setI64(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setFloat(const uint32_t index, const float v) {
  if (index < m_values.size()) {
    m_values[index].setFloat(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setDouble(const uint32_t index, const double v) {
  if (index < m_values.size()) {
    m_values[index].setDouble(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setString(const uint32_t index, const std::string& v) {
  if (index < m_values.size()) {
    m_values[index].setString(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setString(const uint32_t index, const char* v) {
  if (index < m_values.size()) {
    m_values[index].setString(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setString(const uint32_t index, const char* v,
    const int32_t len) {
  if (index < m_values.size()) {
    m_values[index].setString(v, len);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setDecimal(const uint32_t index, const Decimal& v) {
  if (index < m_values.size()) {
    thrift::ColumnValue& cv = m_values[index];
    thrift::Decimal& dec = *cv.initForDecimal();
    v.copyTo(dec);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setDecimal(const uint32_t index, const int8_t signum,
    const int32_t scale, const int8_t* magnitude, const size_t maglen,
    const bool bigEndian) {
  if (index < m_values.size()) {
    thrift::ColumnValue& cv = m_values[index];
    thrift::Decimal& dec = *cv.initForDecimal();

    dec.signum = signum;
    dec.scale = scale;
    if (bigEndian) {
      dec.magnitude.assign((const char*)magnitude, maglen);
    } else {
      // need to inverse the bytes
      if (maglen > 0) {
        dec.magnitude.resize(maglen);
        const int8_t* magp = magnitude + maglen - 1;
        for (uint32_t index = 0; index < maglen; index++, magp--) {
          dec.magnitude[index] = *magp;
        }
      } else {
        dec.magnitude.clear();
      }
    }
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setDate(const uint32_t index, const DateTime v) {
  if (index < m_values.size()) {
    m_values[index].setDate(v.m_secsSinceEpoch);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setTime(const uint32_t index, const DateTime v) {
  if (index < m_values.size()) {
    m_values[index].setTime(v.m_secsSinceEpoch);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setTimestamp(const uint32_t index, const Timestamp& v) {
  if (index < m_values.size()) {
    m_values[index].setTimestamp(v.getEpochTime(), v.getNanos());
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setBinary(const uint32_t index, const std::string& v) {
  if (index < m_values.size()) {
    m_values[index].setBinary(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setBinary(const uint32_t index, const int8_t* v,
    const size_t len) {
  if (index < m_values.size()) {
    m_values[index].setBinary((const char*)v, len);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setArray(const uint32_t index,
    const std::vector<thrift::ColumnValue>& v) {
  if (index < m_values.size()) {
    m_values[index].setARRAY(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setMap(const uint32_t index,
    const std::map<thrift::ColumnValue, thrift::ColumnValue>& v) {
  if (index < m_values.size()) {
    m_values[index].setMAP(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setStruct(const uint32_t index,
    const std::vector<thrift::ColumnValue>& v) {
  if (index < m_values.size()) {
    m_values[index].setSTRUCT(v);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setJSONObject(const uint32_t index,
    const JSONObject& v) {
  if (index < m_values.size()) {
    m_values[index].setJSON(*v.m_p);
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setNull(const uint32_t index) {
  if (index < m_values.size()) {
    m_values[index].setNull();
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setString(const uint32_t index, std::string&& v) {
  if (index < m_values.size()) {
    m_values[index].setString(std::move(v));
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setBinary(const uint32_t index, std::string&& v) {
  if (index < m_values.size()) {
    m_values[index].setBinary(std::move(v));
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setArray(const uint32_t index,
    std::vector<thrift::ColumnValue>&& v) {
  if (index < m_values.size()) {
    m_values[index].setARRAY(std::move(v));
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setMap(const uint32_t index,
    std::map<thrift::ColumnValue, thrift::ColumnValue>&& v) {
  if (index < m_values.size()) {
    m_values[index].setMAP(std::move(v));
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setStruct(const uint32_t index,
    std::vector<thrift::ColumnValue>&& v) {
  if (index < m_values.size()) {
    m_values[index].setSTRUCT(std::move(v));
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}

Parameters& Parameters::setJSONObject(const uint32_t index, JSONObject&& v) {
  if (index < m_values.size()) {
    m_values[index].setJSON(std::move(*v.m_p));
    return *this;
  } else {
    throw GET_SQLEXCEPTION2(SQLStateMessage::LANG_INVALID_PARAM_POSITION_MSG,
        index, m_values.size());
  }
}
