package com.reflecty.testModels;

import com.reflecty.annotations.Singleton;
import com.reflecty.configurations.ObjectProvider;

@Singleton
public interface NodeChainProvider extends ObjectProvider<NodeChain> {}
