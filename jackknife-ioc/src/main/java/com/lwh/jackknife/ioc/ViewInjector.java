/*
 * Copyright (C) 2017 The JackKnife Open Source Project
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

package com.lwh.jackknife.ioc;

import com.lwh.jackknife.ioc.inject.ActivityHandler;
import com.lwh.jackknife.ioc.inject.DialogHandler;
import com.lwh.jackknife.ioc.inject.FragmentHandler;
import com.lwh.jackknife.ioc.bind.BindEvent;
import com.lwh.jackknife.ioc.bind.BindLayout;
import com.lwh.jackknife.ioc.bind.BindType;
import com.lwh.jackknife.ioc.bind.BindView;

import java.util.ArrayList;
import java.util.List;

public class ViewInjector {

    private static ViewInjector sInstance;

    private ViewInjector() {
    }

    private static ViewInjector getInstance() {
        if (sInstance == null) {
            synchronized (ViewInjector.class) {
                if (sInstance == null) {
                    sInstance = new ViewInjector();
                }
            }
        }
        return sInstance;
    }

    public static void inject(SupportV v) {
        getInstance()._inject(v);
    }

    private void _inject(SupportV v) {
        List<BindType> types = new ArrayList<>();
        types.add(new BindLayout(v));
        types.add(new BindView(v));
        types.add(new BindEvent(v));
        for (BindType type:types) {
            dispatchHandler(type, v);
        }
    }

    protected void dispatchHandler(BindType type, SupportV v) {
        if (v instanceof SupportActivity) {
            type.accept(new ActivityHandler());
        } else if (v instanceof SupportFragment) {
            type.accept(new FragmentHandler());
        } else if (v instanceof SupportDialog) {
            type.accept(new DialogHandler());
        }
    }
}
