package com.reflecty.testModels;

public class NodeChain {
    private NodeChainProvider nodeChainProvider;
    private NodeChain nodeChain;

    public NodeChain(NodeChainProvider nodeChainProvider) {
        this.nodeChainProvider = nodeChainProvider;
    }

    public NodeChain next() {
        if(nodeChain==null) nodeChain = nodeChainProvider.getInstance();
        return nodeChain;
    }
}
