/*
 * Copyright 2018 BizStudioSoft.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bizstudio.core.enums;

/**
 *
 * @author ObinnaAsuzu
 */
public enum PageState {
    //NEW Page that has not yet been active
    NEW,
    //Currently displaying page
    ACTIVE,
    //Puased page usally caused by navigation
    PAUSED,
    //If the page is nolonger in the page stack
    INACTIVE
}

