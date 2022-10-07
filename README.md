This problem concerns finding a time when we can do daily recurring maintenance on a server. You are to write a function that is given the following information:

List of times when the server is busy every day
Duration, in minutes, of the desired maintenance window
The function should return the start time of a daily maintenance window when the server is not busy.

In pseudo-code, the function signature would look something like this:
maint_window_start(busy_times, duration_mins) -> start_time

Busy time input
The "busy times" should be a list of time ranges like the following expressed in minutes from midnight:

5 to 30 (00:05 to 00:30)
120 to 241 (02:00 to 04:01)
790 to 1015 (13:10 to 16:55)
