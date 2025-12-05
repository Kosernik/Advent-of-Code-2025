import java.io.*;
import java.util.*;

public class Day05 {
    public static void main(String[] args) {
        String rangesFile = "Day05_ranges";
        String IDsFile = "Day05_IDs";

        String testName = "_test";
        boolean isTest = false;

        if (isTest) {
            rangesFile = rangesFile + testName;
            IDsFile = IDsFile + testName;
        }

        List<String> rangesStr = FileParser.parseFile(rangesFile);
        List<String> idsStr = FileParser.parseFile(IDsFile);

        long[][] ranges = parseRanges(rangesStr);
        long[] ids = parseIDs(idsStr);

        System.out.println("Result 1: " + part1(ranges, ids));
        System.out.println("Result 2: " + part2(ranges));
    }

    private static long part1(long[][] ranges, long[] ids) {
        long result = 0;

        for (long id : ids) {
            for (long[] range : ranges) {
                if (range[0] <= id && id < range[1]) {
                    result++;
                    break;
                }
                if (range[0] > id) {
                    break;
                }
            }
        }

        return result;
    }

    private static long part2(long[][] ranges) {
        long result = 0L;

        for (long[] range : ranges) {
            result += (range[1] - range[0]);
        }

        return result;
    }


    private static long[][] parseRanges(List<String> ranges) {
        long[][] parsed = new long[ranges.size()][2];

        for (int i = 0; i < ranges.size(); i++) {
            var range = ranges.get(i);
            String[] splitted = range.split("-");

            parsed[i][0] = Long.parseLong(splitted[0]);
            parsed[i][1] = Long.parseLong(splitted[1]) + 1L;
        }

        return collapseRanges(parsed);
    }

    private static long[][] collapseRanges(long[][] parsed) {
        List<long[]> collapsed = new ArrayList<>();

        Arrays.sort(parsed, (a, b) -> Long.compare(a[0], b[0]));

        collapsed.add(parsed[0]);
        long prevEnd = parsed[0][1];

        for (int i = 1; i < parsed.length; i++) {
            long[] range = parsed[i];

            if (range[0] <= prevEnd) {
                prevEnd = Math.max(prevEnd, range[1]);
                collapsed.get(collapsed.size() - 1)[1] = prevEnd;
                continue;
            }

            collapsed.add(range);
            prevEnd = range[1];
        }

        long[][] result = new long[collapsed.size()][2];
        for (int i = 0; i < collapsed.size(); i++) {
            result[i] = collapsed.get(i);
        }
        return result;
    }

    private static long[] parseIDs(List<String> ids) {
        long[] parsed = new long[ids.size()];

        for (int i = 0; i < ids.size(); i++) {
            parsed[i] = Long.parseLong(ids.get(i));
        }

        return parsed;
    }
}
