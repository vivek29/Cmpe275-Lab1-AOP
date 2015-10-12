# Cmpe275-Lab1-AOP-SecretShare

This lab is about building a proof-of-concept secret storing and sharing service. 
One can create/read secretes, and share/unshare them with other users as well. The service is a proof of concept in that we
do not intend to turn it into a fully featured system; instead, we leave most of the service implementation as either a
dummy or a placeholder with an empty method, as we only want to concentrate on the access control and logging aspects
with AOP.  

It uses Spring AOP to enforce the following access control, add logging, implement the storeSecret method and any other
method that you deem necessary to satisfy the following requirements: 

1.	One can share the secrets he owns with anybody.
2.	One can only read secrets that are shared with him, or his own secrets. In any other case, an UnauthorizedException is thrown.
3.	If Alice shares her secret with Bob, Bob can further share ir with Carl. 
4.	One can only unshare his own secret. When unsharing a secret with someone that the secret is not shared by any means, the operation is silently ignored. When one attempts to unshare a secret he neither owns or is shared with, an UnauthorizedException is thrown. When attempts to unshare a secret he is shared with but does not own, the request is silently ignored.
5.	Both sharing and unsharing of Alice’s secret with Alice herself have no effect; i.e., Alice always has access to her own secret.
6.	Log a message before each invocation of the read, share, and unshare methods and after the store method, with messages exactly as the following, one message per line, except you need to replace the user IDs with the right ones. All logs go to the console through System.out.
  a.	Alice creates a secrete with ID x
  b.	Alice reads the secret of ID x
  c.	Alice shares the secret of ID x with Carl
  d.	Alice unshares the secret of ID x with Carl

Please note that our access control here assumes that authentication is already taken care of elsewhere, i.e.,
it’s outside the scope of the project to make sure only Alice can call readSecret with userId as “Alice”.


