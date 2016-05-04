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
 * JSONObject.h
 */

#ifndef JSONOBJECT_H_
#define JSONOBJECT_H_

#include "SQLException.h"
#include "WrapperBase.h"

namespace io {
namespace snappydata {
namespace client {
namespace types {

  class JSONObject: public WrapperBase<thrift::JSONObject> {
  private:
    JSONObject(thrift::JSONObject* json) :
        WrapperBase<thrift::JSONObject>(json, false) {
    }

    friend class io::snappydata::client::Row;
    friend class io::snappydata::client::Parameters;
    friend class io::snappydata::client::UpdatableRow;

  public:
    JSONObject();

    JSONObject(const std::string& str);

    JSONObject(const JSONObject& other) :
        WrapperBase<thrift::JSONObject>(other) {
    }

    JSONObject& operator=(const JSONObject& other) {
      WrapperBase<thrift::JSONObject>::operator=(other);
      return *this;
    }
  };

} /* namespace types */
} /* namespace client */
} /* namespace snappydata */
} /* namespace io */

#endif /* JSONOBJECT_H_ */
