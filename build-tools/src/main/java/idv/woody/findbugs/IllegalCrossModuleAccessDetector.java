package idv.woody.findbugs;

import com.google.common.collect.HashMultimap;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import org.apache.bcel.classfile.JavaClass;

public class IllegalCrossModuleAccessDetector extends BytecodeScanningDetector {

    private final BugReporter bugReporter;
    private final String BUG_TYPE = "ICMA_ILLEGAL_CROSS_MODULE_ACCESS";
    private HashMultimap<String, String> dependencyGraph;
    private String clsName;

    public IllegalCrossModuleAccessDetector(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
        this.dependencyGraph = HashMultimap.create();
    }

    @Override
    public void visit(JavaClass obj) {
        clsName = obj.getClassName();
    }

    @Override
    public void sawOpcode(int seen) {
        // INVOKEVIRTUAL: method invocation 182
        // INVOKESTATIC: static method invocation 184
        // GETSTATIC: static reference 178
        // INVOKEINTERFACE: interface invocation 185
        if (((seen == INVOKESTATIC) || (seen == INVOKEVIRTUAL) || (seen == GETSTATIC) || (seen == INVOKEINTERFACE)) && clsName.startsWith("idv.woody")) {
            String refClsName = getClassConstantOperand();
            refClsName = refClsName.replace('/', '.');

            if (!refClsName.startsWith("idv.woody")) {
                // exclude external dependency check
                return;
            }

            if (clsName.equals(refClsName)) {
                return;
            }

            String[] fromModuleSplits = clsName.split("\\.");
            String[] referenceModuleSplits = refClsName.split("\\.");

            boolean innerModuleAccess = fromModuleSplits[2].equals(referenceModuleSplits[2]);
            if (innerModuleAccess) {
                return;
            }

            String referencePackage = referenceModuleSplits[3];
            boolean illegalAccess = (!"api".equals(referencePackage)) && (!"model".equals(referencePackage));
            if (illegalAccess) {
                dependencyGraph.put(clsName, refClsName);
            }
        }
    }

    @Override
    public void report() {
        dependencyGraph.entries().stream().forEach(entry -> {
            BugInstance bug = new BugInstance(this, BUG_TYPE, NORMAL_PRIORITY);
            bug.addClass(entry.getKey());
            bugReporter.reportBug(bug);
        });

        dependencyGraph.clear();
    }

}