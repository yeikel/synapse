/*
 * Copyright 2020 American Express Travel Related Services Company, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.americanexpress.synapse.service.rest.controller.reactive;

import io.americanexpress.synapse.service.rest.controller.BaseController;
import io.americanexpress.synapse.service.rest.controller.reactive.helpers.ReactiveCreateResponseEntityCreator;
import io.americanexpress.synapse.service.rest.model.BaseServiceRequest;
import io.americanexpress.synapse.service.rest.model.BaseServiceResponse;
import io.americanexpress.synapse.service.rest.service.BaseCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * BaseCrudController class specifies the prototypes for listening for requests from the consumer
 * to Create (POST), Update (PUT/PATCH) or Delete (DELETE) a resource.
 *
 * @param <I> input request type
 * @param <O> output response type
 * @param <S> service type
 * @author Gabriel Jimenez
 */
public abstract class BaseReactiveCreateController<I extends BaseServiceRequest, O extends BaseServiceResponse, S extends BaseCreateService<I, O>> extends BaseController<S> {

    @Autowired
    private ReactiveCreateResponseEntityCreator<O> reactiveCreateResponseEntityCreator;

    /**
     * Create a single resource.
     *
     * @param serviceRequest body from the consumer
     * @return response to the consumer
     */
    @PostMapping
    public ResponseEntity<Mono<O>> create(@Valid @RequestBody I serviceRequest) {
        logger.entry(serviceRequest);

        final O serviceResponse = service.create(serviceRequest);
        ResponseEntity<Mono<O>> responseEntity = reactiveCreateResponseEntityCreator.create(serviceResponse);

        logger.exit();
        return responseEntity;
    }
}
