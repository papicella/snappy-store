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
 * ParameterDescriptor.h
 */

#ifndef PARAMETERDESCRIPTOR_H_
#define PARAMETERDESCRIPTOR_H_

#include "ColumnDescriptorBase.h"

namespace io {
namespace snappydata {
namespace client {

  namespace ParameterMode {
    enum type {
      UNKNOWN = 0,
      IN = 1,
      INOUT = 2,
      OUT = 4
    };
  }

  class ParameterDescriptor: public ColumnDescriptorBase {
  private:
    ParameterDescriptor(thrift::ColumnDescriptor& descriptor,
        const uint32_t parameterIndex) :
        ColumnDescriptorBase(descriptor, parameterIndex) {
    }

    friend class PreparedStatement;

  public:
    ParameterMode::type getParameterMode() const throw() {
      if (m_descriptor.parameterIn) {
        return m_descriptor.parameterOut ? ParameterMode::INOUT
            : ParameterMode::IN;
      } else if (m_descriptor.parameterOut) {
        return ParameterMode::OUT;
      } else {
        return ParameterMode::UNKNOWN;
      }
    }

    ~ParameterDescriptor() throw () {
    }
  };

} /* namespace client */
} /* namespace snappydata */
} /* namespace io */

#endif /* PARAMETERDESCRIPTOR_H_ */