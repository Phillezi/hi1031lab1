FROM alpine:latest

ARG GO_FAST_VERSION=0.1.6

RUN apk add --no-cache unzip openssh

# download and unzip go-fast-cdn
ADD https://github.com/kevinanielsen/go-fast-cdn/releases/download/${GO_FAST_VERSION}/go-fast-cdn_${GO_FAST_VERSION}_linux_amd64.zip /tmp/cdn.zip
RUN unzip /tmp/cdn.zip -d /cdn/

EXPOSE 8080

# start go-fast-cdn
CMD ["/cdn/go-fast-cdn"]