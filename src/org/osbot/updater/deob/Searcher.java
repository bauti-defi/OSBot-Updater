package org.osbot.updater.deob;

import org.objectweb.asm.tree.*;

public class Searcher {

    public static int WILDCARD = -1337;
    public static int IF = -13377;
    public static int CONSTPUSH = -133777;
    public static int SHORTIF = -133433;


    private final InsnList instructions;
    AbstractInsnNode[] instruction;

    public Searcher(MethodNode method) {
        instructions = method.instructions;
        instruction = instructions.toArray();
    }

    public int findSingle(int pattern, int Instance) {
        int c = 0;
        for (int I = 0; I < instruction.length; ++I) {
            if (instruction[I].getOpcode() == pattern) {
                if (c == Instance) {
                    return I;
                }
                ++c;
            }
        }
        return -1;
    }

    public int findSingleFieldDesc(int pattern, String value) {
        int l = 0;
        for (int i = 0; l != -1; ++i) {
            l = findSingle(pattern, i);
            if (l != -1)
                if (((FieldInsnNode) instruction[l]).desc.equals(value))
                    return l;
        }
        return -1;
    }

    public int findSingleFieldDesc(int pattern, String value, int startLine) {
        int L = 0;
        for (int I = 0; L != -1; ++I) {
            L = find(new int[]{pattern}, I, startLine);
            if (L != -1) {
                if (((FieldInsnNode) instruction[L]).desc.equals(value))
                    return L;
            }
        }
        return -1;
    }

    public int findSingleIntValue(int pattern, int value) {
        int L = 0;
        //AbstractInsnNode[] instruction = instructions.toArray();
        for (int I = 0; L != -1; ++I) {
            L = findSingle(pattern, I);
            if (L != -1)
                if (((IntInsnNode) instruction[L]).operand == value)
                    return L;
        }
        return -1;
    }

    public int findSingleLdcValue(int pattern, int value) {
        int L = 0;
        //AbstractInsnNode[] instruction = instructions.toArray();
        for (int I = 0; L != -1; ++I) {
            L = findSingle(pattern, I);
            if (L != -1) {
                if (((LdcInsnNode) instruction[L]).cst.equals(value))
                    return L;
            }
        }
        return -1;
    }

    public int findSingleLdcValue(int pattern, long value) {
        int L = 0;
        //AbstractInsnNode[] instruction = instructions.toArray();
        for (int I = 0; L != -1; ++I) {
            L = findSingle(pattern, I);
            if (L != -1) {
                if (((LdcInsnNode) instruction[L]).cst.equals(value))
                    return L;
            }
        }
        return -1;
    }

    public int findSingleIntValue(int pattern, int value, int startLine) {
        int L = 0;
        //AbstractInsnNode[] instruction = instructions.toArray();
        for (int I = 0; L != -1; ++I) {
            L = find(new int[]{pattern}, I, startLine);
            if (L != -1)
                if (((IntInsnNode) instruction[L]).operand == value)
                    return L;
        }
        return -1;
    }

    public int findSingleJump(int Jump, int Pattern, int startLine, int linesUp, int Instance) {
        int C = 0;
        int Counter = 0;
        if (startLine == -1)
            return -1;
        for (int I = startLine; Counter < linesUp; ++I) {
            ++Counter;
            if (instruction[I].getOpcode() == Jump) {
                I = instructions.indexOf(((JumpInsnNode) instruction[I]).label);
            }
            if (instruction[I].getOpcode() == Pattern) {
                if (C == Instance)
                    return I;
                ++C;
            }
        }
        return -1;
    }

    public int findSingleLines(int pattern, int startLine, int linesUp, int Instance) {
        int C = 0;
        if (startLine == -1)
            return -1;
        for (int I = startLine; I < startLine + linesUp; ++I) {
            if (instruction[I].getOpcode() == pattern) {
                if (C == Instance) {
                    return I;
                }
                ++C;
            }
        }
        return -1;
    }

    public int amount(int Code) {
        //AbstractInsnNode[] instruction = instructions.toArray();
        int Count = 0;
        for (int I = 0; I < instruction.length; ++I) {
            if (instruction[I].getOpcode() == Code) {
                ++Count;
            }

        }
        return Count;
    }

    public int find(int Pattern[], int instance) {
        AbstractInsnNode Instructions[] = instructions.toArray();
        int Count = 0;
        for (int i = 0, j = 0; i < Instructions.length; ++i) {
            int k = i, l = j;
            while ((Instructions[k].getOpcode() == Pattern[l] || WILDCARD == Pattern[l])
                    || (IF == Pattern[l] && Instructions[k].getOpcode() > 158 && Instructions[k].getOpcode() < 167) ||
                    (CONSTPUSH == Pattern[l] && Instructions[k].getOpcode() > 0 && Instructions[k].getOpcode() < 18) ||
                    (SHORTIF == Pattern[l] && Instructions[k].getOpcode() > 152 && Instructions[k].getOpcode() < 159)) {
                ++k;
                ++l;

                if (k >= Instructions.length)
                    break;

                if (Instructions[k].getOpcode() == -1)
                    ++k;

                if (l == Pattern.length) {
                    if (Count == instance)
                        return i;
                    else {
                        ++Count;
                        break;
                    }
                }

                if (k == Instructions.length) {
                    if (Count == instance)
                        return j;
                    else {
                        ++Count;
                        break;
                    }
                }
            }
        }
        return -1;
    }

    public int find(int Pattern[], int instance, int startLine) {
        if (startLine == -1)
            return -1;
        AbstractInsnNode Instructions[] = instructions.toArray();
        int Count = 0;
        for (int i = startLine, j = 0; i < Instructions.length; ++i) {
            int k = i, l = j;

            while ((Instructions[k].getOpcode() == Pattern[l] || WILDCARD == Pattern[l])
                    || (IF == Pattern[l] && Instructions[k].getOpcode() > 158 && Instructions[k].getOpcode() < 167) ||
                    (CONSTPUSH == Pattern[l] && Instructions[k].getOpcode() > 0 && Instructions[k].getOpcode() < 18) ||
                    (SHORTIF == Pattern[l] && Instructions[k].getOpcode() > 152 && Instructions[k].getOpcode() < 159)) {
                ++k;
                ++l;

                if (Instructions[k].getOpcode() == -1)
                    ++k;

                if (l == Pattern.length) {
                    if (Count == instance)
                        return i;
                    else {
                        ++Count;
                        break;
                    }
                }

                if (k == Instructions.length) {
                    if (Count == instance)
                        return j;
                    else {
                        ++Count;
                        break;
                    }
                }
            }
        }
        return -1;
    }

    public int find(int[] Pattern, int instance, int startLine, int endLine) {
        if (startLine == -1 || endLine < startLine) {
            return -1;
        }
        AbstractInsnNode Instructions[] = instructions.toArray();
        if (endLine == -1)
            endLine = Instructions.length - 1;

        int Count = 0;
        for (int i = startLine, j = 0; i < Instructions.length && i <= endLine; ++i) {
            int k = i, l = j;

            while ((Instructions[k].getOpcode() == Pattern[l] || WILDCARD == Pattern[l])
                    || (IF == Pattern[l] && Instructions[k].getOpcode() > 158 && Instructions[k].getOpcode() < 167) ||
                    (CONSTPUSH == Pattern[l] && Instructions[k].getOpcode() > 0 && Instructions[k].getOpcode() < 18) ||
                    (SHORTIF == Pattern[l] && Instructions[k].getOpcode() > 152 && Instructions[k].getOpcode() < 159)) {
                ++k;
                ++l;

                if (Instructions[k].getOpcode() == -1)
                    ++k;

                if (l == Pattern.length) {
                    if (Count == instance)
                        return i;
                    else {
                        ++Count;
                        break;
                    }
                }

                if (k == Instructions.length) {
                    if (Count == instance)
                        return j;
                    else {
                        ++Count;
                        break;
                    }
                }
            }
        }
        return -1;
    }

    public int findMultiPatterns(int[][] Patterns, int instance) {
        for (int I = 0; I < Patterns.length; ++I) {
            int L = find(Patterns[I], instance);
            if (L != -1) {
                return L;
            }
        }
        return -1;
    }


}

