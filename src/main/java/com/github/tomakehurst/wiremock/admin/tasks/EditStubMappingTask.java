/*
 * Copyright (C) 2016-2021 Thomas Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tomakehurst.wiremock.admin.tasks;

import com.github.tomakehurst.wiremock.admin.AdminTask;
import com.github.tomakehurst.wiremock.admin.model.PathParams;
import com.github.tomakehurst.wiremock.admin.model.SingleStubMappingResult;
import com.github.tomakehurst.wiremock.core.Admin;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import java.util.UUID;

public class EditStubMappingTask implements AdminTask {

  @Override
  public ResponseDefinition execute(Admin admin, Request request, PathParams pathParams) {
    StubMapping newStubMapping = StubMapping.buildFrom(request.getBodyAsString());
    UUID id = UUID.fromString(pathParams.get("id"));
    SingleStubMappingResult stubMappingResult = admin.getStubMapping(id);
    if (!stubMappingResult.isPresent()) {
      return ResponseDefinition.notFound();
    }

    newStubMapping.setId(id);

    admin.editStubMapping(newStubMapping);
    return ResponseDefinition.okForJson(newStubMapping);
  }
}
